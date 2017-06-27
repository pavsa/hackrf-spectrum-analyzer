package jspectrumanalyzer.core;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.ListEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jspectrumanalyzer.HackRFSweepSpectrumAnalyzer;
import jspectrumanalyzer.Version;
import net.miginfocom.swing.MigLayout;

public class HackRFSweepSettingsUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721079457485020637L;
	private JTextField txtHackrfConnected;

	/**
	 * Create the panel.
	 */
	public HackRFSweepSettingsUI(HackRFSettings hackRFSettings)
	{
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		int minFreq = 1;
		int maxFreq = 7250;
		int freqStep = 1;

		setLayout(new MigLayout("", "[123.00px,grow,leading]", "[][20px][][][20px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Frequency start [MHz]");
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel, "cell 0 0,growx,aligny center");

		FrequencySelectorPanel frequencySelectorStart = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, minFreq);
		add(frequencySelectorStart, "cell 0 1,grow");

		frequencySelectorStart.setValue(hackRFSettings.getFrequencyStart());

		JLabel lblFrequencyEndmhz = new JLabel("Frequency end [MHz]");
		lblFrequencyEndmhz.setForeground(Color.WHITE);
		add(lblFrequencyEndmhz, "cell 0 3,alignx left,aligny center");

		FrequencySelectorPanel frequencySelectorEnd = new FrequencySelectorPanel(minFreq, maxFreq, freqStep, maxFreq);
		add(frequencySelectorEnd, "cell 0 4,grow");
		frequencySelectorEnd.setValue(hackRFSettings.getFrequencyEnd());

		JLabel lblFftBinhz = new JLabel("FFT Bin [Hz]");
		lblFftBinhz.setForeground(Color.WHITE);
		add(lblFftBinhz, "cell 0 6");

		JSpinner spinnerFFTBinHz = new JSpinner();
		spinnerFFTBinHz.setFont(new Font("Monospaced", Font.BOLD, 16));
		spinnerFFTBinHz.setModel(new SpinnerListModel(
				new String[] { "1000", "2000", "5000", "10 000", "20 000", "50 000", "100 000", "200 000", "500 000", "1 000 000", "2 000 000", "5 000 000" }));
		add(spinnerFFTBinHz, "cell 0 7,growx");
		((ListEditor) spinnerFFTBinHz.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
		spinnerFFTBinHz.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setFFTBin(Integer.parseInt(spinnerFFTBinHz.getValue().toString().replaceAll("\\s", "")));
			}
		});
		spinnerFFTBinHz.setValue("100 000");

		JLabel lblGain = new JLabel("Gain [dB]");
		lblGain.setForeground(Color.WHITE);
		add(lblGain, "cell 0 9");

		JSlider sliderGain = new JSlider(JSlider.HORIZONTAL, 0, 100, 2);
		sliderGain.setFont(new Font("Monospaced", Font.BOLD, 16));
		sliderGain.setBackground(Color.BLACK);
		sliderGain.setForeground(Color.WHITE);
		add(sliderGain, "flowy,cell 0 10,growx");

		JLabel lbl_gainValue = new JLabel(hackRFSettings.getGain() + "dB");
		lbl_gainValue.setForeground(Color.WHITE);
		add(lbl_gainValue, "cell 0 10,alignx right");

		sliderGain.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				int val = sliderGain.getValue();
				hackRFSettings.setGain(val);
				lbl_gainValue.setText(
						String.format(" %ddB  [LNA: %ddB  VGA: %ddB]", hackRFSettings.getGain(), hackRFSettings.getLNAGain(), hackRFSettings.getVGAGain()));
			}
		});
		sliderGain.setValue(hackRFSettings.getGain());

		JLabel lblNumberOfSamples = new JLabel("Number of samples");
		lblNumberOfSamples.setForeground(Color.WHITE);
		add(lblNumberOfSamples, "cell 0 12");

		JSpinner spinner_numberOfSamples = new JSpinner();
		spinner_numberOfSamples.setModel(new SpinnerListModel(new String[] { "8192", "16384", "32768", "65536", "131072", "262144" }));
		spinner_numberOfSamples.setFont(new Font("Monospaced", Font.BOLD, 16));
		((ListEditor) spinner_numberOfSamples.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
		((ListEditor) spinner_numberOfSamples.getEditor()).getTextField().setEditable(false);
		;
		add(spinner_numberOfSamples, "cell 0 13,growx");
		
		JCheckBox chckbxAntennaPower = new JCheckBox("Antenna power");
		chckbxAntennaPower.setBackground(Color.BLACK);
		chckbxAntennaPower.setForeground(Color.WHITE);
		add(chckbxAntennaPower, "cell 0 15");
		chckbxAntennaPower.setSelected(hackRFSettings.getAntennaPowerEnable());
		chckbxAntennaPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hackRFSettings.setAntennaPowerEnable(chckbxAntennaPower.isSelected());
			}
		});


		JLabel lblWaterfallPaletteStart = new JLabel("Waterfall palette start [dB]");
		lblWaterfallPaletteStart.setForeground(Color.WHITE);
		add(lblWaterfallPaletteStart, "cell 0 18");

		JSlider slider_waterfallPaletteStart = new JSlider();
		slider_waterfallPaletteStart.setForeground(Color.WHITE);
		slider_waterfallPaletteStart.setBackground(Color.BLACK);
		slider_waterfallPaletteStart.setMinimum(-100);
		slider_waterfallPaletteStart.setMaximum(0);
		slider_waterfallPaletteStart.setValue(-30);
		add(slider_waterfallPaletteStart, "cell 0 19,growx");
		slider_waterfallPaletteStart.setValue(hackRFSettings.getSpectrumPaletteStart());
		slider_waterfallPaletteStart.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSpectrumPaletteStart(slider_waterfallPaletteStart.getValue());
			}
		});

		spinner_numberOfSamples.setValue(hackRFSettings.getSamples() + "");
		spinner_numberOfSamples.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSamples(Integer.parseInt(spinner_numberOfSamples.getValue().toString()));
			}
		});

		JLabel lblWaterfallPaletteLength = new JLabel("Waterfall palette length [dB]");
		lblWaterfallPaletteLength.setForeground(Color.WHITE);
		add(lblWaterfallPaletteLength, "cell 0 21");

		JSlider slider_waterfallPaletteSize = new JSlider(HackRFSweepSpectrumAnalyzer.SPECTRUM_PALETTE_SIZE_MIN, 100);
		slider_waterfallPaletteSize.setBackground(Color.BLACK);
		slider_waterfallPaletteSize.setForeground(Color.WHITE);
		add(slider_waterfallPaletteSize, "cell 0 22,growx");

		slider_waterfallPaletteSize.setValue(hackRFSettings.getSpectrumPaletteSize());

		slider_waterfallPaletteSize.addChangeListener(new ChangeListener()
		{
			@Override public void stateChanged(ChangeEvent e)
			{
				hackRFSettings.setSpectrumPaletteSize(slider_waterfallPaletteSize.getValue());
			}
		});

		FrequencyRangeSelector frequencyRangeSelector = new FrequencyRangeSelector(frequencySelectorStart, frequencySelectorEnd, new PropertyChangeListener()
		{
			@Override public void propertyChange(PropertyChangeEvent evt)
			{
				hackRFSettings.setFrequency(frequencySelectorStart.getValue(), frequencySelectorEnd.getValue());
			}
		});

		JCheckBox chckbxShowPeaks = new JCheckBox("Show peaks");
		chckbxShowPeaks.setForeground(Color.WHITE);
		chckbxShowPeaks.setBackground(Color.BLACK);
		add(chckbxShowPeaks, "cell 0 24,growx");
		
		JCheckBox chckbxRemoveSpurs = new JCheckBox("Spur filter (may distort real signals)");
		chckbxRemoveSpurs.setForeground(Color.WHITE);
		chckbxRemoveSpurs.setBackground(Color.BLACK);
		add(chckbxRemoveSpurs, "cell 0 26");
		
		txtHackrfConnected = new JTextField();
		txtHackrfConnected.setText("HackRF connected");
		txtHackrfConnected.setForeground(Color.WHITE);
		txtHackrfConnected.setBackground(Color.BLACK);
		add(txtHackrfConnected, "cell 0 29,growx");
		txtHackrfConnected.setColumns(10);
		txtHackrfConnected.setBorder(null);
		
		JButton btnPause = new JButton("Pause");
		add(btnPause, "cell 0 31,growx");
		btnPause.setBackground(Color.black);
		
		JButton btnAbout = new JButton("Visit homepage");
		btnAbout.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				 if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                try {
		                    URI uri = new URI(Version.url);
		                    desktop.browse(uri);
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
		        }
			}
		});
		
		Label labelVersion = new Label("Version: v"+Version.version);
		add(labelVersion, "flowx,cell 0 42");
		btnAbout.setBackground(Color.BLACK);
		add(btnAbout, "cell 0 42,alignx right");
		btnPause.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setCapturing(!hackRFSettings.isCapturing());	
			}
		});
		hackRFSettings.registerListener(new HackRFSettings.HackRFEventAdapter()
		{
			@Override public void captureStateChanged(boolean isCapturing)
			{
				btnPause.setText(isCapturing ? "Pause"  : "Resume");
			}
			@Override public void hardwareStatusChanged(boolean hardwareSendingData)
			{
				txtHackrfConnected.setText("HackRF "+(hardwareSendingData ? "connected":"disconnected"));
			}
		});;
		
		JCheckBox chckbxFilterSpectrum = new JCheckBox("Filter spectrum");
		chckbxFilterSpectrum.setBackground(Color.BLACK);
		chckbxFilterSpectrum.setForeground(Color.WHITE);
		//		add(chckbxFilterSpectrum, "cell 0 23");

		chckbxShowPeaks.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setChartPeaksVisibility(chckbxShowPeaks.isSelected());
			}
		});

		chckbxFilterSpectrum.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setFilterSpectrum(chckbxFilterSpectrum.isSelected());
			}
		});
		
		chckbxRemoveSpurs.setSelected(hackRFSettings.isSpurRemoval());
		chckbxRemoveSpurs.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				hackRFSettings.setSpurRemoval(chckbxRemoveSpurs.isSelected());
			}
		});
	}

}
