package jspectrumanalyzer.nativebridge;

public interface HackRFSweepDataCallback
{
	public void newSpectrumData(boolean sweeStarted, double frequencyStart[], float fftBinWidthHz, float signalPowerdBm[]);
}
