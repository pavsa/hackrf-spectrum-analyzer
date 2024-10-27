package jspectrumanalyzer.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Simple spur removal filter.
 */
public class SpurFilter
{
	private final DatasetSpectrum		avgSpectrum;
	private boolean						calibrated		= false;
	private int							debug			= 0;
	/**
	 * contains spur correction power values 
	 */
	private final DatasetSpectrum		filter;
	private ArrayList<DatasetSpectrum>	filterInputs	= new ArrayList<>();
	private final DatasetSpectrum		input;
	private final int					maxPeakBins;
	/**
	 * to be marked as a spur, the power value fft bin 
	 * should not fall outside of this variable's value 
	 * from average value during calibration   
	 */
	private final float					maxPeakJitterdB;
	private final DatasetSpectrum		noiseFloor;
	private final float					peakThresholdAboveNoise;
	private final int					validIterations;

	public SpurFilter(float maxPeakJitterdB, float peakThresholdAboveNoise, int maxPeakBins, int validIterations, DatasetSpectrum input)
	{
		this.maxPeakJitterdB = maxPeakJitterdB;
		this.peakThresholdAboveNoise = peakThresholdAboveNoise;
		this.maxPeakBins = maxPeakBins;
		this.validIterations = validIterations;
		this.input = input;
		this.filter = new DatasetSpectrum(input.getFFTBinSizeHz(), input.getFreqStartMHz(), input.getFreqStopMHz(), 0);
		this.avgSpectrum = input.cloneMe();
		this.noiseFloor = avgSpectrum.cloneMe();
	}

	/**
	 * Filters input dataset with the calibrated filter data
	 */
	public void filterDataset()
	{
		if (input.getFFTBinSizeHz() != filter.getFFTBinSizeHz() || input.spectrumLength() != filter.spectrumLength())
		{
			throw new IllegalArgumentException("Input dataset not the same size as output dataset");
		}

		if (calibrated == false)
		{
			calibrate();
			return;
		}

		filterDatasetExec();

		return;
	}

	public boolean isFilterCalibrated()
	{
		return calibrated;
	}

	public void recalibrate()
	{
		calibrated = false;
		filterInputs.clear();
	}

	private void calibrate()
	{
		if (calibrated)
			return;
		filterInputs.add(input.cloneMe());

		//int validIterations	= 20;
		//float peakThresholdAboveNoise	= 4;
		//float maxPeakJitterdB	= 6;

		if (filterInputs.size() >= validIterations)
		{
			/**
			 * trigger calibration
			 */
			avgSpectrum.setSpectrumInitPower(0);
			avgSpectrum.resetSpectrum();

			/**
			 * use different noise floor for different portions of the spectrum
			 * due to non-linear sensitivity in different bands 
			 */
			float[] noiseFloorArr = noiseFloor.getSpectrumArray();

			/**
			 * Calculate average values 
			 */
			float[] avgSpectrArray = avgSpectrum.getSpectrumArray();
			for (DatasetSpectrum datasetSpectrum : filterInputs)
			{
				float[] spectr = datasetSpectrum.getSpectrumArray();
				for (int i = 0; i < spectr.length; i++)
				{
					avgSpectrArray[i] += spectr[i];
				}
			}
			for (int i = 0; i < avgSpectrArray.length; i++)
			{
				avgSpectrArray[i] /= validIterations;
			}

			LinkedList<Integer> spurIndexes = new LinkedList<>();
			int end = avgSpectrArray.length - maxPeakBins;
			double emaNoiseFloor = avgSpectrArray[0];
			double emaNoiseFloorOrder = Math.max(5, noiseFloorArr.length / 50);

			Arrays.fill(noiseFloorArr, 0, maxPeakBins, (float) emaNoiseFloor);
			/**
			 * find spur candidates
			 */
			for (int i = maxPeakBins; i < end; i++)
			{
				float currAvgVal = avgSpectrArray[i];

				boolean triggered1 = false;
				for (int j = i - maxPeakBins; j < i && triggered1 == false; j++)
				{
					//					if (currAvgVal - avgSpectrArray[j] >= peakThresholdAboveNoise)
					if (currAvgVal - emaNoiseFloor >= peakThresholdAboveNoise)
						triggered1 = true;
				}

				boolean triggered2 = false;
				for (int j = i + 1; j <= i + maxPeakBins && triggered2 == false; j++)
				{
					//					if (currAvgVal - avgSpectrArray[j] >= peakThresholdAboveNoise)
					if (currAvgVal - emaNoiseFloor >= peakThresholdAboveNoise)
						triggered2 = true;
				}

				if (triggered1 && triggered2)
				{
					spurIndexes.add(i);
				}
				else
				{
					//update only with not spur values
					emaNoiseFloor = EMA.calculate(currAvgVal, emaNoiseFloor, emaNoiseFloorOrder);
				}
				noiseFloorArr[i] = (float) emaNoiseFloor;
			}
			Arrays.fill(noiseFloorArr, end, noiseFloorArr.length, (float) emaNoiseFloor);

			/**
			 * check if spurs candidates all fit below maxPeakJitterdB
			 * spurs should be more or less stable
			 */
			for (Iterator<Integer> iterator = spurIndexes.iterator(); iterator.hasNext();)
			{
				Integer spurIndex = iterator.next();
				boolean valid = true;
				for (DatasetSpectrum datasetSpectrum : filterInputs)
				{
					float[] spectr = datasetSpectrum.getSpectrumArray();
					float diff = Math.abs(spectr[spurIndex] - avgSpectrArray[spurIndex]);
					if (diff > maxPeakJitterdB)
					{
						valid = false;
						break;
					}
				}
				if (!valid)
				{
					iterator.remove();
					continue;
				}
			}

			Arrays.fill(filter.getSpectrumArray(), 0);
			for (Integer s : spurIndexes)
			{
				float spurAboveNoise = avgSpectrArray[s] - noiseFloorArr[s];
				filter.getSpectrumArray()[s] = spurAboveNoise;
			}
			calibrated = true;
			return;
		}
		else
			calibrated = false;
	}

	private void filterDatasetExec()
	{
		float input[] = this.input.getSpectrumArray();
		/**
		 * 0 = normal operation
		 * 1 = output averaged spectrum
		 * 2 = noise floor
		 * 3 = noise floor + output spur corrections
		 */
		debug = 0;
		if (debug == 0)
		{
			for (int i = 0; i < input.length; i++)
			{
				input[i] -= filter.getSpectrumArray()[i];
			}
		}
		else
		{
			if (debug == 1)
			{
				for (int i = 0; i < input.length; i++)
				{
					input[i] = avgSpectrum.getSpectrumArray()[i];
				}
			}
			else if (debug == 2)
			{
				for (int i = 0; i < input.length; i++)
				{
					input[i] = noiseFloor.getSpectrumArray()[i];
				}
			}
			else if (debug == 3)
			{
				for (int i = 0; i < input.length; i++)
				{
					input[i] = noiseFloor.getSpectrumArray()[i] + filter.getSpectrumArray()[i];
				}
			}
			else if (debug == 4)
			{

			}
		}
	}
}
