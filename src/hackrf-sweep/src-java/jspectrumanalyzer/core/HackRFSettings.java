package jspectrumanalyzer.core;

public interface HackRFSettings
{
	public static abstract class HackRFEventAdapter implements HackRFEventListener {
		@Override public void captureStateChanged(boolean isCapturing)
		{
			
		}
		@Override public void hardwareStatusChanged(boolean hardwareSendingData)
		{
			
		}
	}

	public static interface HackRFEventListener{
		public void captureStateChanged(boolean isCapturing);
		public void hardwareStatusChanged(boolean hardwareSendingData);
	}

	public int getFFTBinHz();

	/**
	 * Get sweep frequency end MHz 
	 * @return
	 */
	public int getFrequencyEnd();

	/**
	 * Get sweep frequency start MHz 
	 * @return
	 */
	public int getFrequencyStart();

	public int getGain();

	public int getLNAGain();

	public int getSamples();

	public int getSpectrumPaletteSize();

	public int getSpectrumPaletteStart();
	
	public boolean isSpurRemoval();

	public int getVGAGain();

	public boolean isCapturing();

	public boolean isChartsPeaksVisible();

	public boolean isFilterSpectrum();

	public void registerListener(HackRFEventListener listener);

	public void removeListener(HackRFEventListener listener);

	public void setCapturing(boolean pause);

	public void setChartPeaksVisibility(boolean visible);

	public void setFFTBin(int fftBinHz);
	
	public void setFilterSpectrum(boolean filter);
	
	public void setFrequency(int freqStartMHz, int freqEndMHz);
	public void setGain(int gaindB);
	
	public void setSamples(int samples);
	public void setSpectrumPaletteSize(int dB);
	
	public void setSpectrumPaletteStart(int dB);
	public void setSpurRemoval(boolean enable);
	
	public void setAntennaPowerEnable(boolean enable);
	public boolean getAntennaPowerEnable();
}
