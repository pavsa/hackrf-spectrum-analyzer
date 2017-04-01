package jspectrumanalyzer.core;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class DatasetSpectrum implements Cloneable
{
	protected  final float	fftBinSizeHz;
	protected  final long	freqStartHz;
	protected  final int	freqStartMHz;
	protected  final int	freqStopMHz;

	protected  float[]		spectrum;
	protected  float		spectrumInitPower;
	
	protected ArrayList<ArrayList<XYDataItem>> cachedDataItems	= new ArrayList<>();
	protected int cachedDataItemsIndex	= 0;
	
	/**
	 * Inits
	 * @param fftBinSizeHz
	 * @param freqStartMHz
	 * @param freqStopMHz
	 * @param spectrumInitPower 
	 * @param peaks enable calculation of peaks
	 * @param peakFallThreshold
	 * @param peakFalloutMillis
	 */
	public DatasetSpectrum(float fftBinSizeHz, int freqStartMHz, int freqStopMHz, float spectrumInitPower)
	{
		this.fftBinSizeHz = fftBinSizeHz;
		this.freqStartMHz = freqStartMHz;
		this.freqStartHz = freqStartMHz * 1000000l;
		this.freqStopMHz = freqStopMHz;
		this.spectrumInitPower = spectrumInitPower;
		int datapoints = (int) (Math.ceil(freqStopMHz - freqStartMHz) * 1000000d / fftBinSizeHz);
		spectrum = new float[datapoints];
		Arrays.fill(spectrum, spectrumInitPower);

		for (int j = 0; j < 5; j++) {
			ArrayList<XYDataItem> list	= new ArrayList<>();
			for (int i = 0; i < datapoints; i++) {
				double freq = (freqStartHz + fftBinSizeHz * i) / 1000000;
				list.add(new XYDataItem(freq, 0));
			}
			cachedDataItems.add(list);
		}
	}
	
	@Override protected Object clone() throws CloneNotSupportedException
	{
		DatasetSpectrum copy	= (DatasetSpectrum) super.clone();
		copy.spectrum			= spectrum.clone();
		return copy;
	}
	
	public DatasetSpectrum cloneMe()
	{
		DatasetSpectrum copy;
		try
		{
			copy = (DatasetSpectrum) clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
		return copy;
	}

	/**
	 * Adds new data to spectrum's dataset
	 * @param fftBins
	 * @return true if the whole spectrum was refreshed once
	 */
	public boolean addNewData(FFTBins fftBins)
	{
		boolean triggerRefresh = false;
		triggerRefresh	= fftBins.fullSweepDone;

		for (int binsIndex = 0; binsIndex < fftBins.freqStart.length; binsIndex++)
		{
			double freqStart = fftBins.freqStart[binsIndex];
			int spectrIndex = (int) ((freqStart - freqStartHz) / fftBinSizeHz);
			if (spectrIndex < 0 || spectrIndex >= spectrum.length)
				continue;
			spectrum[spectrIndex] = fftBins.sigPowdBm[binsIndex];
		}
		

		return triggerRefresh;
	}

	/**
	 * Fills data to {@link XYSeries}, uses x units in MHz
	 * @param series
	 */
	public void fillToXYSeries(XYSeries series)
	{
		fillToXYSeriesPriv(series, spectrum);
	}

	protected void fillToXYSeriesPriv(XYSeries series, float[] spectrum){
		series.clear();
		/**
		 * caching decreases GC usage
		 */
		boolean useCached	= false;
		if (!useCached){
			for (int i = 0; i < spectrum.length; i++)
			{
				double freq = (freqStartHz + fftBinSizeHz * i) / 1000000;
				series.add(freq, spectrum[i]);
			}
		}
		else{
			ArrayList<XYDataItem> items	= cachedDataItems.get(cachedDataItemsIndex);
			for (int i = 0; i < spectrum.length; i++)
			{
				XYDataItem item	= items.get(i);
				item.setY(spectrum[i]);
				series.add(item);
			}
			cachedDataItemsIndex	= (cachedDataItemsIndex+1)%cachedDataItems.size();
		}
	}
	
	public float getFFTBinSizeHz()
	{
		return fftBinSizeHz;
	}

	public int getFreqStartMHz()
	{
		return freqStartMHz;
	}

	public int getFreqStopMHz()
	{
		return freqStopMHz;
	}

	/**
	 * Translates index of spectrum to frequency in Hz
	 * @param index
	 * @return
	 */
	public double getFrequency(int index)
	{
		double freq = (freqStartHz + fftBinSizeHz * index);
		return freq;
	}

	public float getPower(int index)
	{
		return spectrum[index];
	}

	public void resetSpectrum()
	{
		Arrays.fill(spectrum, spectrumInitPower);
	}
	public float[] getSpectrumArray()
	{
		return spectrum;
	}
	
	public int spectrumLength()
	{
		return spectrum.length;
	}

	public void setSpectrumInitPower(float spectrumInitPower)
	{
		this.spectrumInitPower = spectrumInitPower;
	}
	
	/**
	 * Copies spectrum to destination dataset
	 * @param filtered
	 */
	public void copyTo(DatasetSpectrum filtered)
	{
		System.arraycopy(spectrum, 0, filtered.spectrum, 0, spectrum.length);
	}
}
