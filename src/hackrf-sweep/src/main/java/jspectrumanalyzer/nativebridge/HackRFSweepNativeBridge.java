package jspectrumanalyzer.nativebridge;

import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;

import hackrfsweep.HackrfSweepLibrary;
import hackrfsweep.HackrfSweepLibrary.hackrf_sweep_lib_start__fft_power_callback_callback;

public class HackRFSweepNativeBridge
{
	public static final String			JNA_LIBRARY_NAME	= "hackrf-sweep";
	public static final NativeLibrary	JNA_NATIVE_LIB;
	static
	{
		/**
		 * to make sure unpacked jnidispatch.dll is properly loaded
		 * jnidispatch.dll is used directly instead of JNA bundled jar, because it is much faster to load
		 */
		String pathPrefix	= "./"+Platform.RESOURCE_PREFIX+"/";
		System.setProperty("jna.boot.library.path", pathPrefix);
		System.setProperty("jna.nosys", "true");
//		Native.DEBUG_JNA_LOAD	= true;
//		Native.DEBUG_LOAD	= true;
		
		NativeLibrary.addSearchPath(JNA_LIBRARY_NAME, pathPrefix);
		JNA_NATIVE_LIB		= NativeLibrary.getInstance(JNA_LIBRARY_NAME);
		Native.register(HackrfSweepLibrary.class, JNA_NATIVE_LIB);		

	}

	public static synchronized void start(HackRFSweepDataCallback dataCallback, int freq_min_MHz, int freq_max_MHz, int fft_bin_width, int num_samples,
			int lna_gain, int vga_gain, boolean antennaPowerEnable, boolean internalLNA)
	{
		hackrf_sweep_lib_start__fft_power_callback_callback callback = new hackrf_sweep_lib_start__fft_power_callback_callback()
		{
			@Override public void apply(byte sweep_started, int bins, DoubleByReference freqStart, float fftBinWidth, FloatByReference powerdBm)
			{
				double[] freqStartArr = bins == 0 ? null : freqStart.getPointer().getDoubleArray(0, bins);
				float[] powerArr =  bins == 0 ? null : powerdBm.getPointer().getFloatArray(0, bins);
				dataCallback.newSpectrumData(sweep_started==0 ? false : true, freqStartArr, fftBinWidth, powerArr);
			}
		};
		Native.setCallbackThreadInitializer(callback, new CallbackThreadInitializer(true));

		HackrfSweepLibrary.hackrf_sweep_lib_start(callback, freq_min_MHz, freq_max_MHz, fft_bin_width, num_samples, lna_gain, vga_gain, antennaPowerEnable ? 1 : 0, internalLNA ? 1 : 0);
	}

	public static void stop()
	{
		HackrfSweepLibrary.hackrf_sweep_lib_stop();
	}
}
