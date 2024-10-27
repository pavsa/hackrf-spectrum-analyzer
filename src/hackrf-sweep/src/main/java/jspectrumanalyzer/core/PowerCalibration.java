package jspectrumanalyzer.core;

public class PowerCalibration 
{
	public final double offset_dB;
	public final double gain;
	public PowerCalibration(double expectedPower_dBm, double measuredPower_dBm, double measuredAtGain) 
	{
		offset_dB	= expectedPower_dBm - measuredPower_dBm;
		this.gain	= measuredAtGain;
	}
	public double getOffset_dB(double gain){
		return offset_dB + (this.gain - gain);
	}
	
	public static void correctPower(PowerCalibration cal, double gain, FFTBins bins){
//		bins.fftBinWidthHz
		double offset	= cal.getOffset_dB(gain);
		for (int i = 0; i < bins.sigPowdBm.length; i++) {
			bins.sigPowdBm[i]	+= offset; 
		}
	}
}
