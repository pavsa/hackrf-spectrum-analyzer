package jspectrumanalyzer.core;

import java.awt.Color;
import java.awt.image.BufferedImage;

import jspectrumanalyzer.ui.GraphicsToolkit;
import jspectrumanalyzer.ui.HotIronBluePalette;
import shared.mvc.ModelValue;

public class PersistentDisplay {
	/**
	 * Image represented by single float array
	 */
	private static class FloatImage {
		private final float[]	data;
		private final int		width, height;

		public FloatImage(int width, int height) {
			data = new float[width * height];
			this.width = width;
			this.height = height;
		}

		public float add(int x, int y, float power) {
			return data[y * width + x] += power;
		}

		public float get(int x, int y) {
			return data[y * width + x];
		}

		public int getIndex(int x, int y) {
			return y * width + x;
		}

		public void multiplyAllValues(float value) {
			for (int i = 0; i < data.length; i++) {
				data[i] *= value;
			}
		}

		public void set(int x, int y, float value) {
			data[y * width + x] = value;
		}

		public void subtractAllValues(float value) {
			for (int i = 0; i < data.length; i++) {
				data[i] -= value;
			}
		}
	}

	public static float map(float in, float in_min, float in_max, float out_min, float out_max) {
		return (in - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static int map(int x, int in_min, int in_max, int out_min, int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	private boolean						calibrated			= false;
	private boolean						calibrating			= false;
	private long						calibrationStarted	= 0;
	private final long					calibrationTime		= 1000;
	private ModelValue<BufferedImage>	displayImage		= new ModelValue<BufferedImage>("", null);
	private FloatImage					imagePowerAccumulated;
	private int							incomingDataCounter	= 0;
	private HotIronBluePalette			palette				= new HotIronBluePalette();
	private int							persistenceTimeSecs	= 5;
	private float						updatesPerSecond	= 1;

	public PersistentDisplay() {
		setImageSize(320, 240);
	}

	public void drawSpectrum2(DatasetSpectrum datasetSpectrum, float yMin, float yMax, boolean renderImage) {
		drawSpectrumFloat(datasetSpectrum, yMin, yMax, renderImage);
	}

	public void drawSpectrumFloat(DatasetSpectrum datasetSpectrum, float yMin, float yMax, boolean renderImage) {
		if (!calibrated) {
			if (!calibrating) {
				calibrating = true;
				calibrationStarted = System.currentTimeMillis();
				incomingDataCounter = 0;
			} else {
				incomingDataCounter++;
				long t = System.currentTimeMillis() - calibrationStarted;
				if (t >= calibrationTime) {
					updatesPerSecond = (float) incomingDataCounter / (t / 1000f);
					int bins = (int) ((datasetSpectrum.getFreqStopMHz() - datasetSpectrum.getFreqStartMHz()) * 1000000l
							/ datasetSpectrum.getFFTBinSizeHz());
					BufferedImage image = displayImage.getValue();
					if (bins < image.getWidth()) {
						setImageSize(bins, image.getHeight());
					}
					calibrated = true;
					calibrating = false;

					if (updatesPerSecond < 1)
						updatesPerSecond = 1;
				}
			}
			return;
		}

		BufferedImage image = this.displayImage.getValue();
		FloatImage imagePowerAccumulated = this.imagePowerAccumulated;

		if (image == null)
			return;

		float rawImagePowerArr[] = imagePowerAccumulated.data;

		/**
		 * EMA
		 */
		float order = persistenceTimeSecs * updatesPerSecond;
		float k = 2f / (order + 1f);
		//		double result = currentValue * k + previousEMA * (1 - k);
		float kM1 = 1 - k; /* apply decay only */
		imagePowerAccumulated.multiplyAllValues(kM1);

		float[] spectrum = datasetSpectrum.getSpectrumArray();
		int width = image.getWidth();
		int height = image.getHeight();
		float hDivYRange = (-height) / (yMax - yMin);

		/**
		 * pipeline: float image accumulates power for each pixel, then the
		 * power value gets converted to color based on the hot iron palette
		 */
		float maxAccumulatedValue = updatesPerSecond * persistenceTimeSecs;
		for (int i = 0; i < spectrum.length; i++) {
			float power = spectrum[i];
			float powerLin = 1; /*
								 * each occurence of power value at given
								 * frequency is simply +1
								 */

			int x = i * width / spectrum.length;
			int y = //(power - yMin) * (0 - height) / (yMax - yMin) + height; 
					(int) ((power - yMin) * hDivYRange
							+ height); /* optimized map() */

			if (x >= 0 && y >= 0 && x < width && y < height) {
				int index = imagePowerAccumulated.getIndex(x, y);
				if (imagePowerAccumulated.data[index] < maxAccumulatedValue)
					imagePowerAccumulated.data[index] += powerLin;
			}
		}

		/**
		 * render image only when requested
		 */
		if (renderImage) {
			/**
			 * Find the max value to properly scale
			 */
			float maxValue = Float.MIN_NORMAL;
			for (int i = 0; i < rawImagePowerArr.length; i++) {
				float value = rawImagePowerArr[i];
				if (value > maxValue)
					maxValue = value;
			}

			/**
			 * Fill the image with black color
			 */
			//			Graphics2D g	= image.createGraphics();
			//			g.setColor(Color.red);
			//			g.fillRect(0, 0, width, height);
			//			g.dispose();
			//			renderImage	= false;
			//			

			float setToZeroThreshold = 0.01f;
			float minOutToLog = 1.0f;
			float maxOutToLog = 100;
			float logMin = (float) Math.log10(minOutToLog);
			float logMax = (float) Math.log10(maxOutToLog);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float val = imagePowerAccumulated.get(x, y);
					if (val < setToZeroThreshold) {
						imagePowerAccumulated.set(x, y, 0);
						val = 0;
					}

					if (val == 0) {
						image.setRGB(x, y, Color.black.getRGB());
					} else {
						float outPower = val;

						/**
						 * Log compressed values
						 */
						outPower = (float) Math.log10(map(outPower, 0, maxValue, minOutToLog, maxOutToLog));
						float normalized = map(outPower, logMin, logMax, 0.15f, 0.95f); //(imagePowerAccumulated.get(x, y)) / (maxValue);

						/**
						 * linear values
						 */
						//						float normalized	= map(outPower, 0, maxValue, 0.4f, 0.9f); //(imagePowerAccumulated.get(x, y)) / (maxValue);

						Color color = palette.getColorNormalized(normalized);

						image.setRGB(x, y, color.getRGB());
						//						g.setColor(color);
						//						g.drawLine(x, y, x, y);
					}
				}
			}

		}
	}

	public ModelValue<BufferedImage> getDisplayImage() {
		return displayImage;
	}

	public int getPersistenceTime() {
		return persistenceTimeSecs;
	}

	public void reset() {
		BufferedImage image = displayImage.getValue();
		if (image != null) {
			setImageSize(image.getWidth(), image.getHeight());
		}
	}

	public void setImageSize(int width, int height) {
		if (width < 1 || height < 1)
			return;

		calibrated = false;
		calibrating = false;

		System.out.println("Persistent image set to " + width + "x" + height);
		displayImage.setValue(GraphicsToolkit.createAcceleratedImageOpaque(width, height));
		imagePowerAccumulated = new FloatImage(width, height);
	}

	public void setPersistenceTime(int persistenceTimeSecs) {
		this.persistenceTimeSecs = persistenceTimeSecs;
	}
}
