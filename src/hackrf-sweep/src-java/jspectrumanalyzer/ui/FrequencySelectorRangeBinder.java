package jspectrumanalyzer.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import jspectrumanalyzer.core.FrequencyRange;
import jspectrumanalyzer.core.HackRFSettings;

/**
 * Limits frequency selection of two selectors (start/end)
 */
public class FrequencySelectorRangeBinder
{
	public FrequencySelectorPanel selFreqStart, selFreqEnd;
	public FrequencySelectorRangeBinder(FrequencySelectorPanel selFreqStart, FrequencySelectorPanel selFreqEnd)
	{
		this.selFreqEnd	= selFreqEnd;
		this.selFreqStart	= selFreqStart;
		VetoableChangeListener freqStartVetoable = evt -> {
			Integer newVal = (Integer) evt.getNewValue();
			if (newVal >= selFreqEnd.getValue())
			{
				//try to increase freq end by the same value
				if (!selFreqEnd.setValue(selFreqEnd.getValue() + (newVal - (Integer) evt.getOldValue())))
					throw new PropertyVetoException(">", evt);
			}
		};
		VetoableChangeListener freqEndVetoable = evt -> {
			Integer newVal = (Integer) evt.getNewValue();
			if (newVal <= selFreqStart.getValue())
			{
				if (!selFreqStart.setValue(selFreqStart.getValue() - ((Integer) evt.getOldValue() - newVal)))
					throw new PropertyVetoException(">", evt);
			}
		};

		selFreqEnd.addVetoableChangeListener(freqEndVetoable);
		selFreqStart.addVetoableChangeListener(freqStartVetoable);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		selFreqStart.addPropertyChangeListener("value", propertyChangeListener);
		selFreqEnd.addPropertyChangeListener("value", propertyChangeListener);
	}
	
	public FrequencyRange getFrequencyRange() {
		return new FrequencyRange(selFreqStart.getValue(), selFreqEnd.getValue());
	}
}
