package jspectrumanalyzer.core;

/**
 * Storage for raw data coming out of hackrf_sweep
 */
public class FFTBins
{
	public final float	fftBinWidthHz, sigPowdBm[];
	public final double	freqStart[];
	public final boolean fullSweepDone;
	public FFTBins(boolean fullSweepDone, double frequencyStart[], float fftBinWidthHz, float signalPowerdBm[])
	{
		this.fullSweepDone	= fullSweepDone;
		this.fftBinWidthHz = fftBinWidthHz;
		this.freqStart = frequencyStart;
		this.sigPowdBm = signalPowerdBm;
	}
}