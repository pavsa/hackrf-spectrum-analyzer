package jspectrumanalyzer.nativebridge;

public interface HackRFSweepDataCallback
{
	/**
	 * Called by native code when new spectrum data is available 
	 * @param sweepStarted
	 * @param frequencyStart array of fft bin's start frequencies, null if no data 
	 * @param fftBinWidthHz
	 * @param signalPowerdBm array of fft bin's power in dB, null if no data
	 */
	public void newSpectrumData(boolean sweepStarted, double frequencyStart[], float fftBinWidthHz, float signalPowerdBm[]);
}
