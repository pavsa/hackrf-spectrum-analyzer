package jspectrumanalyzer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.StandardTickUnitSource;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.event.OverlayChangeListener;
import org.jfree.chart.panel.Overlay;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import jspectrumanalyzer.core.DatasetSpectrum;
import jspectrumanalyzer.core.DatasetSpectrumPeak;
import jspectrumanalyzer.core.FFTBins;
import jspectrumanalyzer.core.HackRFSettings;
import jspectrumanalyzer.core.HackRFSweepSettingsUI;
import jspectrumanalyzer.core.PowerCalibration;
import jspectrumanalyzer.core.SpurFilter;
import jspectrumanalyzer.core.WaterfallPlot;
import jspectrumanalyzer.nativebridge.HackRFSweepDataCallback;
import jspectrumanalyzer.nativebridge.HackRFSweepNativeBridge;

public class HackRFSweepSpectrumAnalyzer implements HackRFSettings, HackRFSweepDataCallback
{
	public static final int SPECTRUM_PALETTE_SIZE_MIN = 5;

	public static void main(String[] args) throws IOException
	{
//		System.out.println(new File("").getAbsolutePath());
		new HackRFSweepSpectrumAnalyzer();
	}

	private XYSeriesCollection			dataset					= new XYSeriesCollection();
	private JFreeChart					chart					= ChartFactory.createXYLineChart("Spectrum analyzer", "Frequency [MHz]", "Power [dB]", dataset,
			PlotOrientation.VERTICAL, false, false, false);

	private DatasetSpectrumPeak			datasetSpectrum;
	private int							dropped					= 0;
	private JFrame						f;
	private boolean						filterSpectrum;
	private int							lnaGain;
	private ReentrantLock				lock					= new ReentrantLock();
	private int							parameterFFTBinHz		= 200000;
	private int							parameterGaindB			= 40;
	private int							parameterMaxFreqMHz		= 2500;
	private int							parameterMinFreqMHz		= 2400;
	private int							parameterSamples		= 8192;
	private boolean						parameterAntPower		= false;
	private ArrayBlockingQueue<FFTBins>	processingQueue			= new ArrayBlockingQueue<>(1000);
	private boolean						showPeaks				= false;
	private float						spectrumInitValue		= -150;
	private boolean 					spurRemoval				= false;
	private Thread						threadHackrfSweep;
	private ArrayBlockingQueue<Integer>	threadLaunchCommands	= new ArrayBlockingQueue<>(1);

	private Thread						threadLauncher;
	private Thread						threadProcessing;
	private int							vgaGain;
	private ValueMarker					waterfallPaletteEndMarker;
	private ValueMarker					waterfallPaletteStartMarker;

	private WaterfallPlot				waterfallPlot;

	boolean								isChartDrawing			= false;
	private ArrayList<HackRFEventListener>	listeners				= new ArrayList<>();
	private boolean isCapturing	= true;

	public HackRFSweepSpectrumAnalyzer()
	{
		int axisWidthLeft = 70;
		int axisWidthRight = 20;

		recalculateGains(parameterGaindB);

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		/**
		 * Color palette for UI 
		 */
		Color palette0 = Color.white;
		Color palette1 = new Color(0xe5e5e5);
		Color palette2 = new Color(0xFCA311);
		Color palette3 = new Color(0x14213D);
		Color palette4 = Color.BLACK;

		XYPlot plot = chart.getXYPlot();
		NumberAxis domainAxis = ((NumberAxis) plot.getDomainAxis());
		NumberAxis rangeAxis = ((NumberAxis) plot.getRangeAxis());
		XYLineAndShapeRenderer rend = new XYLineAndShapeRenderer();
		rend.setBaseShapesVisible(false);

		chartPanel = new ChartPanel(chart);
		chartPanel.setMaximumDrawWidth(4096);
		chartPanel.setMaximumDrawHeight(2160);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		chartPanel.setPopupMenu(null);
		chartPanel.setMinimumSize(new Dimension(200, 200));

		/**
		 * Draws overlay of waterfall's color scale next to main spectrum chart to show 
		 */
		chartPanel.addOverlay(new Overlay()
		{
			@Override public void addChangeListener(OverlayChangeListener listener)
			{
			}

			@Override public void paintOverlay(Graphics2D g, ChartPanel chartPanel)
			{

				Rectangle2D area = chartPanel.getChartRenderingInfo().getPlotInfo().getDataArea();
				int plotStartX = (int) area.getX();
				int plotWidth = (int) area.getWidth();

				Rectangle2D subplotArea = chartPanel.getChartRenderingInfo().getPlotInfo().getDataArea();

				int y1 = (int) plot.getRangeAxis().valueToJava2D(waterfallPlot.getSpectrumPaletteStart(), subplotArea, plot.getRangeAxisEdge());
				int y2 = (int) plot.getRangeAxis().valueToJava2D(waterfallPlot.getSpectrumPaletteStart() + waterfallPlot.getSpectrumPaletteSize(), subplotArea,
						plot.getRangeAxisEdge());

				int x = plotStartX + plotWidth;
				int w = 15;
				int h = y1 - y2;
				waterfallPlot.drawScale(g, x, y2, w, h);
			}

			@Override public void removeChangeListener(OverlayChangeListener listener)
			{
			}
		});

		//Align the waterfall plot and the spectrum chart
		chart.addChangeListener(new ChartChangeListener()
		{
			@Override public void chartChanged(ChartChangeEvent event)
			{
				Rectangle2D area = chartPanel.getChartRenderingInfo().getPlotInfo().getDataArea();
				int plotStartX = (int) area.getX();
				int plotWidth = (int) area.getWidth();
				waterfallPlot.setDrawingOffsets(plotStartX, plotWidth);
				//				System.out.println("l off "+offLeft+"  width "+chartWidth );
			}
		});

		addChartMouseMarkers();
		
		waterfallPlot = new WaterfallPlot(chartPanel, 300);

		waterfallPaletteStartMarker = new ValueMarker(waterfallPlot.getSpectrumPaletteStart(), palette2, new BasicStroke(1f));
		waterfallPaletteEndMarker = new ValueMarker(waterfallPlot.getSpectrumPaletteStart() + waterfallPlot.getSpectrumPaletteSize(), palette2,
				new BasicStroke(1f));
		//		chart.getXYPlot().addRangeMarker(waterfallPaletteStartMarker);
		//		chart.getXYPlot().addRangeMarker(waterfallPaletteEndMarker);

		HackRFSweepSettingsUI settingsPanel = new HackRFSweepSettingsUI(this);

		f = new JFrame();
		f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.setTitle("Spectrum Analyzer - hackrf_sweep");
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, chartPanel, waterfallPlot);
		splitPane.setResizeWeight(0.8);
		splitPane.setBorder(null);
		f.add(splitPane, BorderLayout.CENTER);
		f.setMinimumSize(new Dimension(600, 600));
		f.add(settingsPanel, BorderLayout.EAST);
		try
		{
			f.setIconImage(new ImageIcon("program.png").getImage());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}
		f.pack();
		f.setVisible(true);

		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(-110, 0);
		rangeAxis.setTickUnit(new NumberTickUnit(10, new DecimalFormat("###")));

		domainAxis.setNumberFormatOverride(new DecimalFormat(" #.### "));

		rend.setAutoPopulateSeriesStroke(false);
		rend.setAutoPopulateSeriesPaint(false);
		rend.setSeriesPaint(0, palette2);

		if (false)
			chart.addProgressListener(new ChartProgressListener()
			{
				StandardTickUnitSource tus = new StandardTickUnitSource();

				@Override public void chartProgress(ChartProgressEvent event)
				{
					if (event.getType() == ChartProgressEvent.DRAWING_STARTED)
					{
						Range r = domainAxis.getRange();
						domainAxis.setTickUnit((NumberTickUnit) tus.getCeilingTickUnit(r.getLength() / 20));
						domainAxis.setMinorTickCount(2);
						domainAxis.setMinorTickMarksVisible(true);

					}
				}
			});

		plot.setDomainGridlinesVisible(false);
		plot.setRenderer(rend);

		/**
		 * sets empty space around the plot
		 */
		AxisSpace axisSpace = new AxisSpace();
		axisSpace.setLeft(axisWidthLeft);
		axisSpace.setRight(axisWidthRight);
		axisSpace.setTop(20);
		axisSpace.setBottom(50);
		plot.setFixedDomainAxisSpace(axisSpace);//sets width of the domain axis left/right
		plot.setFixedRangeAxisSpace(axisSpace);//sets heigth of range axis top/bottom

		rangeAxis.setAxisLineVisible(false);
		rangeAxis.setTickMarksVisible(false);

		plot.setAxisOffset(RectangleInsets.ZERO_INSETS); //no space between range axis and plot

		Font labelFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		rangeAxis.setLabelPaint(palette1);
		rangeAxis.setTickLabelPaint(palette1);
		domainAxis.setLabelFont(labelFont);
		domainAxis.setTickLabelFont(labelFont);
		domainAxis.setLabelPaint(palette1);
		domainAxis.setTickLabelPaint(palette1);
		rend.setBasePaint(Color.white);
		plot.setBackgroundPaint(palette4);
		chart.setBackgroundPaint(palette4);
		rend.setSeriesPaint(1, palette1);

		startLauncherThread();
		restartHackrfSweep();
		
		//shutdown on exit
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			@Override public void run()
			{
				stopHackrfSweep();
			}
		}));
	}

	private void addChartMouseMarkers() {
		ValueMarker freqMarker	= new ValueMarker(0, Color.WHITE, new BasicStroke(1f));
		freqMarker.setLabelPaint(Color.white);
		freqMarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		freqMarker.setLabelTextAnchor(TextAnchor.TOP_LEFT);
		freqMarker.setLabelFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		ValueMarker signalMarker	= new ValueMarker(0, Color.WHITE, new BasicStroke(1f));
		signalMarker.setLabelPaint(Color.white);
		signalMarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		signalMarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
		signalMarker.setLabelFont(new Font(Font.MONOSPACED, Font.BOLD, 16));

		chartPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x	= e.getX();
				int y 	= e.getY();

				XYPlot plot	= chart.getXYPlot();
				Rectangle2D subplotArea = chartPanel.getChartRenderingInfo().getPlotInfo().getDataArea();
				double crosshairRange	= plot.getRangeAxis().java2DToValue(y, subplotArea, plot.getRangeAxisEdge());
				signalMarker.setValue(crosshairRange);
				signalMarker.setLabel(String.format("%.1fdB", crosshairRange));
				double crosshairDomain	= plot.getDomainAxis().java2DToValue(x, subplotArea, plot.getDomainAxisEdge());
				freqMarker.setValue(crosshairDomain);
				freqMarker.setLabel(String.format("%.1fMHz", crosshairDomain));
			}
		});
		chartPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				chart.getXYPlot().clearDomainMarkers();
				chart.getXYPlot().clearRangeMarkers();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				chart.getXYPlot().clearDomainMarkers();
				chart.getXYPlot().clearRangeMarkers();
				chart.getXYPlot().addRangeMarker(signalMarker);
				chart.getXYPlot().addDomainMarker(freqMarker);
			}
		});
	}

	public boolean isHWSendingData	= false;
	private void fireHardwareStateChanged(boolean sendingData)
	{
		if (this.isHWSendingData != sendingData){
			this.isHWSendingData	= sendingData;
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override public void run()
				{
					synchronized (listeners)
					{
						for (HackRFEventListener hackRFEventListener : listeners)
						{
							try
							{
								hackRFEventListener.hardwareStatusChanged(sendingData);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
	}

	private void fireCapturingStateChanged(boolean isCapturing)
	{
		if (!this.isCapturing != isCapturing){
			this.isCapturing	= isCapturing;
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override public void run()
				{
					synchronized (listeners)
					{
						for (HackRFEventListener hackRFEventListener : listeners)
						{
							try
							{
								hackRFEventListener.captureStateChanged(isCapturing);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
	}

	@Override
	public boolean getAntennaPowerEnable() {
		return parameterAntPower;
	}
	
	@Override
	public void setAntennaPowerEnable(boolean enable) {
		this.parameterAntPower	= enable;
		restartHackrfSweep();
	}
	
	@Override public int getFFTBinHz()
	{
		return parameterFFTBinHz;
	}

	@Override public int getFrequencyEnd()
	{
		return parameterMaxFreqMHz;
	}

	@Override public int getFrequencyStart()
	{
		return parameterMinFreqMHz;
	}

	@Override public int getGain()
	{
		return parameterGaindB;
	}

	@Override public int getLNAGain()
	{
		return lnaGain;
	}

	@Override public int getSamples()
	{
		return parameterSamples;
	}

	@Override public int getSpectrumPaletteSize()
	{
		return (int) waterfallPlot.getSpectrumPaletteSize();
	}

	@Override public int getSpectrumPaletteStart()
	{
		return (int) waterfallPlot.getSpectrumPaletteStart();
	}

	@Override public int getVGAGain()
	{
		return vgaGain;
	}

	@Override public boolean isChartsPeaksVisible()
	{
		return showPeaks;
	}

	@Override public boolean isFilterSpectrum()
	{
		return filterSpectrum;
	}

	@Override public void newSpectrumData(boolean fullSweepDone, double[] frequencyStart, float fftBinWidthHz, float[] signalPowerdBm)
	{
		//		System.out.println(frequencyStart+" "+fftBinWidthHz+" "+signalPowerdBm);
		fireHardwareStateChanged(true);
		if (!processingQueue.offer(new FFTBins(fullSweepDone, frequencyStart, fftBinWidthHz, signalPowerdBm)))
		{
			System.out.println("queue full");
			dropped++;
		}
	}

	@Override public void setChartPeaksVisibility(boolean visible)
	{
		this.showPeaks = visible;
		DatasetSpectrumPeak p = datasetSpectrum;
		if (p != null)
		{
			p.resetPeaks();
		}
	}

	@Override public void setFFTBin(int fftBinHz)
	{
		this.parameterFFTBinHz = fftBinHz;
		restartHackrfSweep();
	}

	@Override public void setFilterSpectrum(boolean filter)
	{
		filterSpectrum = filter;
	}

	@Override public void setFrequency(int freqStartMHz, int freqEndMHz)
	{
		parameterMinFreqMHz = freqStartMHz;
		parameterMaxFreqMHz = freqEndMHz;
		restartHackrfSweep();
	}

	@Override public void setGain(int gaindB)
	{
		if (gaindB == this.parameterGaindB)
			return;
		recalculateGains(gaindB);
		restartHackrfSweep();
	}

	@Override public void setSamples(int samples)
	{
		this.parameterSamples = samples;
		restartHackrfSweep();
	}

	@Override public void setSpectrumPaletteSize(int dB)
	{
		if (dB < SPECTRUM_PALETTE_SIZE_MIN)
			return;
		waterfallPlot.setSpectrumPaletteSize(dB);
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				waterfallPaletteStartMarker.setValue(waterfallPlot.getSpectrumPaletteStart());
				waterfallPaletteEndMarker.setValue(waterfallPlot.getSpectrumPaletteStart() + waterfallPlot.getSpectrumPaletteSize());
			}
		});
	}

	@Override public void setSpectrumPaletteStart(int dB)
	{
		waterfallPlot.setSpectrumPaletteStart(dB);
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override public void run()
			{
				waterfallPaletteStartMarker.setValue(waterfallPlot.getSpectrumPaletteStart());
				waterfallPaletteEndMarker.setValue(waterfallPlot.getSpectrumPaletteStart() + waterfallPlot.getSpectrumPaletteSize());
			}
		});
	}
	
	@Override public void setSpurRemoval(boolean enable)
	{
		this.spurRemoval	= enable;
		SpurFilter filter	= spurFilter;
		if (filter != null){
			filter.recalibrate();
		}
	}
	
	@Override public boolean isSpurRemoval()
	{
		return this.spurRemoval;
	}

	@Override public void registerListener(HackRFEventListener listener)
	{
		listeners.add(listener);
	}
	
	@Override public void removeListener(HackRFEventListener listener)
	{
		listeners.remove(listener);
	}
	
	@Override public void setCapturing(boolean capturing)
	{
		isCapturing	= capturing;
		fireCapturingStateChanged(isCapturing);
	}
	
	@Override public boolean isCapturing()
	{
		return isCapturing;
	}
	
	private void processingThread()
	{
		int counter = 0;

		//mainWhile:
		//while(true)
		{
			FFTBins bin1 = null;
			try
			{
				bin1 = processingQueue.take();
			}
			catch (InterruptedException e1)
			{
				return;
			}
			float binHz = bin1.fftBinWidthHz;

//			PowerCalibration calibration	 = new PowerCalibration(-45, -12.5, 40); 
			
			datasetSpectrum 		= new DatasetSpectrumPeak(binHz, parameterMinFreqMHz, parameterMaxFreqMHz, spectrumInitValue, 15, 30000);
			chart.getXYPlot().getDomainAxis().setRange(parameterMinFreqMHz, parameterMaxFreqMHz);
			
			float maxPeakJitterdB	= 6;
			float peakThresholdAboveNoise = 4;
			int maxPeakBins	= 4;
			int validIterations	= 25;
			spurFilter = new SpurFilter(maxPeakJitterdB, peakThresholdAboveNoise, maxPeakBins, validIterations, datasetSpectrum);

			
			long lastChartUpdated = System.currentTimeMillis();
			long lastScanStartTime = System.currentTimeMillis();
			double lastFreq = 0;

			while (true)
			{
				try
				{
					counter++;
					FFTBins bins = processingQueue.take();
					if (!isCapturing)
						continue;
					boolean triggerChartRefresh = bins.fullSweepDone;
					//continue;
					
					if (bins.freqStart != null && bins.sigPowdBm != null)
					{
//						PowerCalibration.correctPower(calibration, parameterGaindB, bins);
						datasetSpectrum.addNewData(bins);
					}

					if ((triggerChartRefresh/*  || timeDiff > 1000*/))
					{
						//						System.out.println("ctr "+counter+" dropped "+dropped);
						
						/**
						 * filter first
						 */
						if (spurRemoval){
							spurFilter.filterDataset();
						}
						/**
						 * after filtering, calculate peak spectrum
						 */
						if (showPeaks){
							datasetSpectrum.refreshPeakSpectrum();
							waterfallPlot.setStatusMessage(String.format("Total Spectrum Peak Power %.1fdBm", datasetSpectrum.calculateSpectrumPeakPower()));
						}
						
						XYSeries spectrumSeries = new XYSeries("spectrum");
						spectrumSeries.setNotify(false);
						datasetSpectrum.fillToXYSeries(spectrumSeries);
						spectrumSeries.setNotify(true);

						XYSeries spectrumPeaks;
						if (showPeaks)
						{
							spectrumPeaks = new XYSeries("peaks");
							spectrumPeaks.setNotify(false);
							datasetSpectrum.fillPeaksToXYSeries(spectrumPeaks);
							spectrumPeaks.setNotify(false);
						}
						else
							spectrumPeaks = new XYSeries("peaks");

						SwingUtilities.invokeLater(new Runnable()
						{
							@Override public void run()
							{
								chart.setNotify(false);
								dataset.removeAllSeries();
								dataset.addSeries(spectrumPeaks);
								dataset.addSeries(spectrumSeries);
								chart.setNotify(true);
							}
						});
						waterfallPlot.addNewData(datasetSpectrum);

						counter = 0;
						lastChartUpdated = System.currentTimeMillis();
					}

				}
				catch (InterruptedException e)
				{
					return;
				}
			}

		}

	}

	private void recalculateGains(int totalGain)
	{
		/**
		 * use only lna gain when <=40 
		 * when >40, add only vga gain
		 */
		lnaGain = totalGain / 8 * 8; //lna gain step 8, max 40
		if (lnaGain > 40)
			lnaGain = 40;
		vgaGain = lnaGain != 40 ? 0 : ((totalGain - lnaGain) & ~1); //vga gain step 2, max 60
		this.parameterGaindB = lnaGain + vgaGain;
	}

	/**
	 * uses fifo queue to process launch commands, only the last launch command is important, delete others
	 */
	private synchronized void restartHackrfSweep()
	{
		if (threadLaunchCommands.offer(0) == false)
		{
			threadLaunchCommands.clear();
			threadLaunchCommands.offer(0);
		}
	}
	
	private volatile boolean forceStopSweep	 = false;
	private SpurFilter spurFilter;
	private ChartPanel chartPanel;
	/**
	 * no need to synchronize, executes only in launcher thread
	 */
	private void restartHackrfSweepExecute()
	{
		stopHackrfSweep();
		threadHackrfSweep = new Thread(new Runnable()
		{
			@Override public void run()
			{
				Thread.currentThread().setName("hackrf_sweep");
				try
				{
					forceStopSweep	= false;
					sweep();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		threadHackrfSweep.start();
	}

	private void startLauncherThread()
	{
		threadLauncher = new Thread(new Runnable()
		{
			@Override public void run()
			{
				Thread.currentThread().setName("Launcher-thread");
				while (true)
				{
					try
					{
						threadLaunchCommands.take();
						restartHackrfSweepExecute();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		threadLauncher.start();
	}

	/**
	 * no need to synchronize, executes only in launcher thread
	 */
	private void stopHackrfSweep()
	{
		forceStopSweep	= true;
		if (threadHackrfSweep != null)
		{
			while (threadHackrfSweep.isAlive())
			{
				forceStopSweep	= true;
//				System.out.println("Calling HackRFSweepNativeBridge.stop()");
				HackRFSweepNativeBridge.stop();
				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException e)
				{
				}
			}
			try
			{
				threadHackrfSweep.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			threadHackrfSweep = null;
		}
		System.out.println("HackRFSweep thread stopped.");
		if (threadProcessing != null)
		{
			threadProcessing.interrupt();
			try
			{
				threadProcessing.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			threadProcessing = null;
			System.out.println("Processing thread stopped.");
		}
	}

	private void sweep() throws IOException
	{
		lock.lock();
		try
		{
			threadProcessing = new Thread(new Runnable()
			{
				@Override public void run()
				{
					Thread.currentThread().setName("hackrf_sweep data processing thread");
					processingThread();
				}
			});
			threadProcessing.start();

			/**
			 * Ensures auto-restart if HW disconnects
			 */
			while(forceStopSweep == false){
				System.out.println("Starting hackrf_sweep... " + parameterMinFreqMHz + "-" + parameterMaxFreqMHz + "MHz ");
				System.out.println("hackrf_sweep params:  freq " + parameterMinFreqMHz + "-" + parameterMaxFreqMHz + "MHz  samples " + parameterSamples + "  lna: "
						+ lnaGain + " vga: " + vgaGain);
				fireHardwareStateChanged(false);
				HackRFSweepNativeBridge.start(this, parameterMinFreqMHz, parameterMaxFreqMHz, parameterFFTBinHz, parameterSamples, lnaGain, vgaGain, parameterAntPower);
				fireHardwareStateChanged(false);
				if (forceStopSweep==false){
					Thread.sleep(1000);
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
			fireHardwareStateChanged(false);
		}
	}
}
