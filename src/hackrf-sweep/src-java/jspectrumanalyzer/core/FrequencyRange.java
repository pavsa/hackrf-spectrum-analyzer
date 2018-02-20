package jspectrumanalyzer.core;

public class FrequencyRange{
	private final int startMHz, endMHz;

	public FrequencyRange(int startMHz, int endMHz) {
		this.startMHz = startMHz;
		this.endMHz = endMHz;
	}
	public int getEndMHz() {
		return endMHz;
	}
	public int getStartMHz() {
		return startMHz;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FrequencyRange) {
			FrequencyRange fr	= (FrequencyRange)obj;
			if (fr.endMHz == endMHz && fr.startMHz == startMHz)
				return true;
		}
		return false;
	}
}