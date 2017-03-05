package jspectrumanalyzer.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

/**
 * Limits frequency selection of two selectors (start/end)
 */
public class FrequencyRangeSelector
{
	public FrequencyRangeSelector(FrequencySelectorPanel selFreqStart, FrequencySelectorPanel selFreqEnd, PropertyChangeListener propertyChangeListener)
	{
		VetoableChangeListener freqStartVetoable = new VetoableChangeListener()
		{
			@Override public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
			{
				Integer newVal = (Integer) evt.getNewValue();
				if (newVal >= (Integer) selFreqEnd.getValue())
				{
					//try to increase freq end by the same value
					if (!selFreqEnd.setValue(selFreqEnd.getValue() + (newVal - (Integer) evt.getOldValue())))
						throw new PropertyVetoException(">", evt);
				}
			}
		};
		VetoableChangeListener freqEndVetoable = new VetoableChangeListener()
		{
			@Override public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
			{
				Integer newVal = (Integer) evt.getNewValue();
				if (newVal <= (Integer) selFreqStart.getValue())
				{
					if (!selFreqStart.setValue(selFreqStart.getValue() - ((Integer) evt.getOldValue() - newVal)))
						throw new PropertyVetoException(">", evt);
				}
			}
		};

		selFreqStart.addPropertyChangeListener("value", propertyChangeListener);
		selFreqEnd.addPropertyChangeListener("value", propertyChangeListener);

		selFreqEnd.addVetoableChangeListener(freqEndVetoable);
		selFreqStart.addVetoableChangeListener(freqStartVetoable);
	}
}
