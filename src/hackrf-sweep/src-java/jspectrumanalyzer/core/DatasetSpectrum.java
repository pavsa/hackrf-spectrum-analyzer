package jspectrumanalyzer.core;

import java.util.Arrays;

import org.jfree.data.xy.XYSeries;

public class DatasetSpectrum
{
	private final float	fftBinSizeHz;
	private final long	freqStartHz;
	private final int	freqStartMHz;
	private final int	freqStopMHz;
	private long		lastAdded			= System.currentTimeMillis();
	private long		peakFalloutMillis	= 1000;
	private float		peakFallThreshold;
	private boolean		peaks				= false;

	/**
	 * stores real peaks and if {@link #spectrumPeak} falls more than preset value below it, start using values from {@link #spectrumPeak}
	 */
	private float[]		spectrPeakDisplay;
	private float[]		spectrum;
	private float		spectrumInitPower;

	/**
	 * stores EMA decaying peaks
	 */
	private float[]		spectrumPeak;

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
	public DatasetSpectrum(float fftBinSizeHz, int freqStartMHz, int freqStopMHz, float spectrumInitPower, boolean peaks, float peakFallThreshold,
			long peakFalloutMillis)
	{
		this.fftBinSizeHz = fftBinSizeHz;
		this.freqStartMHz = freqStartMHz;
		this.freqStartHz = freqStartMHz * 1000000l;
		this.freqStopMHz = freqStopMHz;
		this.peaks = peaks;
		this.peakFalloutMillis = peakFalloutMillis;
		this.spectrumInitPower = spectrumInitPower;
		this.peakFallThreshold = peakFallThreshold;
		int datapoints = (int) (Math.ceil(freqStopMHz - freqStartMHz) * 1000000d / fftBinSizeHz);
		spectrum = new float[datapoints];
		Arrays.fill(spectrum, spectrumInitPower);
		spectrumPeak = new float[datapoints];
		Arrays.fill(spectrumPeak, spectrumInitPower);
		spectrPeakDisplay = new float[datapoints];
		Arrays.fill(spectrPeakDisplay, spectrumInitPower);
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
		long timeDiffFromPrevValueMillis = System.currentTimeMillis() - lastAdded;
		lastAdded = System.currentTimeMillis();

		if (peaks)
		{
			peakFallThreshold = 10;
			for (int binsIndex = 0; binsIndex < fftBins.freqStart.length; binsIndex++)
			{
				double freqStart = fftBins.freqStart[binsIndex];
				int spectrIndex = (int) ((freqStart - freqStartHz) / fftBinSizeHz);
				if (spectrIndex < 0 || spectrIndex >= spectrum.length)
					continue;

				if (fftBins.sigPowdBm[binsIndex] > spectrPeakDisplay[spectrIndex])
				{
					spectrPeakDisplay[spectrIndex] = spectrumPeak[spectrIndex] = fftBins.sigPowdBm[binsIndex];
				}

				spectrumPeak[spectrIndex] = (float) EMA.calculateTimeDependent(fftBins.sigPowdBm[binsIndex], spectrumPeak[spectrIndex],
						timeDiffFromPrevValueMillis, peakFalloutMillis);
				if (spectrPeakDisplay[spectrIndex] - spectrumPeak[spectrIndex] > peakFallThreshold)
				{
					spectrPeakDisplay[spectrIndex] = spectrumPeak[spectrIndex];
				}

//				double trigger = Math.abs(freqStart - freqStartHz);
//				if (!triggerRefresh && trigger < fftBinSizeHz)
//				{
//					triggerRefresh = true;
//					//					System.out.println("triggered refresh "+(long)freqStart+"/"+freqStartHz+" trigger: "+trigger);
//				}
			}
		}

		for (int binsIndex = 0; binsIndex < fftBins.freqStart.length; binsIndex++)
		{
			double freqStart = fftBins.freqStart[binsIndex];
			int spectrIndex = (int) ((freqStart - freqStartHz) / fftBinSizeHz);
			if (spectrIndex < 0 || spectrIndex >= spectrum.length)
				continue;
			spectrum[spectrIndex] = fftBins.sigPowdBm[binsIndex];

//			double trigger = Math.abs(freqStart - freqStartHz);
//			if (!triggerRefresh && trigger < fftBinSizeHz)
//			{
//				triggerRefresh = true;
//				//				System.out.println("triggered refresh "+(long)freqStart+"/"+freqStartHz+" trigger: "+trigger);
//			}
		}

		return triggerRefresh;
	}

	/**
	 * Fills data to {@link XYSeries}, uses x units in MHz
	 * @param series
	 */
	public void fillToXYSeries(XYSeries series, boolean peaks)
	{
		series.clear();
		float[] spectrum = peaks ? spectrPeakDisplay : this.spectrum;
		for (int i = 0; i < spectrum.length; i++)
		{
			double freq = (freqStartHz + fftBinSizeHz * i) / 1000000;
			series.add(freq, spectrum[i]);
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

	public boolean isPeaks()
	{
		return peaks;
	}

	public void resetData()
	{
		for (int i = 0; i < spectrum.length; i++)
		{
			spectrum[i] = spectrumInitPower;
		}

	}

	public void resetPeaks()
	{
		Arrays.fill(spectrumPeak, spectrumInitPower);
		Arrays.fill(spectrPeakDisplay, spectrumInitPower);
	}

	/**
	 * Enable peak processing
	 * @param peaks
	 */
	public void setPeaks(boolean peaks)
	{
		this.peaks = peaks;
	}

	public int spectrumLength()
	{
		return spectrum.length;
	}
}
