package jspectrumanalyzer.core;

import java.util.Arrays;

import org.jfree.data.xy.XYSeries;

import jspectrumanalyzer.core.jfc.XYSeriesImmutable;

public class DatasetSpectrumPeak extends DatasetSpectrum
{
	protected long		lastAdded			= System.currentTimeMillis();
	protected long		peakFalloutMillis	= 1000;
	protected float		peakFallThreshold;
	/**
	 * stores EMA decaying peaks
	 */
	protected float[]	spectrumPeak;

	/**
	 * stores real peaks and if {@link #spectrumPeak} falls more than preset value below it, start using values from {@link #spectrumPeak}
	 */
	protected float[]	spectrumPeakHold;
	
	public DatasetSpectrumPeak(float fftBinSizeHz, int freqStartMHz, int freqStopMHz, float spectrumInitPower, float peakFallThreshold, long peakFalloutMillis)
	{
		super(fftBinSizeHz, freqStartMHz, freqStopMHz, spectrumInitPower);

		this.peakFalloutMillis = peakFalloutMillis;
		this.spectrumInitPower = spectrumInitPower;
		this.peakFallThreshold = peakFallThreshold;
		int datapoints = (int) (Math.ceil(freqStopMHz - freqStartMHz) * 1000000d / fftBinSizeHz);
		spectrum = new float[datapoints];
		Arrays.fill(spectrum, spectrumInitPower);
		spectrumPeak = new float[datapoints];
		Arrays.fill(spectrumPeak, spectrumInitPower);
		spectrumPeakHold = new float[datapoints];
		Arrays.fill(spectrumPeakHold, spectrumInitPower);
		

	}

	public void setPeakFalloutMillis(long peakFalloutMillis) {
		this.peakFalloutMillis = peakFalloutMillis;
	}
	
	public void copyTo(DatasetSpectrumPeak filtered)
	{
		super.copyTo(filtered);
		System.arraycopy(spectrumPeak, 0, filtered.spectrumPeak, 0, spectrumPeak.length);
		System.arraycopy(spectrumPeakHold, 0, filtered.spectrumPeakHold, 0, spectrumPeakHold.length);
	}

	/**
	 * Fills data to {@link XYSeries}, uses x units in MHz
	 * @param series
	 */
	public void fillPeaksToXYSeries(XYSeries series)
	{
		fillToXYSeriesPriv(series, spectrumPeakHold);
//		fillToXYSeriesPriv(series, spectrumPeak);
	}
	

	public XYSeriesImmutable createPeaksDataset(String name) {
		float[] xValues	= new float[spectrum.length];
		float[] yValues	= spectrumPeakHold;
		for (int i = 0; i < spectrum.length; i++)
		{
			float freq = (freqStartHz + fftBinSizeHz * i) / 1000000f;
			xValues[i]	= freq;
		}
		XYSeriesImmutable xySeriesF	= new XYSeriesImmutable(name, xValues, yValues);
		return xySeriesF;
	}

	public double calculateSpectrumPeakPower(){
		double powerSum	= 0;
		for (int i = 0; i < spectrumPeakHold.length; i++) {
			powerSum	+= Math.pow(10, spectrumPeakHold[i]/10); /*convert dB to mW to sum power in linear form*/
		}
		powerSum	= 10*Math.log10(powerSum); /*convert back to dB*/ 
		return powerSum;
	}
	
	private long debugLastPeakRerfreshTime	= 0;
	public void refreshPeakSpectrum()
	{
		if (false) {
			long debugMinPeakRefreshTime	= 100;
			if (System.currentTimeMillis()-debugLastPeakRerfreshTime < debugMinPeakRefreshTime)
				return;
			debugLastPeakRerfreshTime	= System.currentTimeMillis();
		}
		
		long timeDiffFromPrevValueMillis = System.currentTimeMillis() - lastAdded;
		if (timeDiffFromPrevValueMillis < 1)
			timeDiffFromPrevValueMillis = 1;
		
		lastAdded = System.currentTimeMillis();
		
//		peakFallThreshold = 10;
//		peakFalloutMillis	= 30000;
		for (int spectrIndex = 0; spectrIndex < spectrum.length; spectrIndex++)
		{
			float spectrumVal = spectrum[spectrIndex];
			if (spectrumVal > spectrumPeakHold[spectrIndex])
			{
				spectrumPeakHold[spectrIndex] = spectrumPeak[spectrIndex] = spectrumVal;
			}

			spectrumPeak[spectrIndex] = (float) EMA.calculateTimeDependent(spectrumVal, spectrumPeak[spectrIndex], timeDiffFromPrevValueMillis,
					peakFalloutMillis);
			if (spectrumPeakHold[spectrIndex] - spectrumPeak[spectrIndex] > peakFallThreshold)
			{
				spectrumPeakHold[spectrIndex] = spectrumPeak[spectrIndex];
			}
		}
	}

	public void resetPeaks()
	{
		Arrays.fill(spectrumPeak, spectrumInitPower);
		Arrays.fill(spectrumPeakHold, spectrumInitPower);
	}

	@Override protected Object clone() throws CloneNotSupportedException
	{
		DatasetSpectrumPeak copy = (DatasetSpectrumPeak) super.clone();
		copy.spectrumPeakHold = spectrumPeakHold.clone();
		copy.spectrumPeak = spectrumPeak.clone();
		return super.clone();
	}

}
