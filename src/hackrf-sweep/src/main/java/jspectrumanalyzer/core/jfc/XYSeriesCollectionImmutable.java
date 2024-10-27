package jspectrumanalyzer.core.jfc;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * {@link XYSeriesCollection} for use with {@link XYSeriesImmutable}
 */
public class XYSeriesCollectionImmutable extends XYSeriesCollection {
	
	@Override
    public double getXValue(int series, int item) {
        return ((XYSeriesImmutable)getSeries(series)).getXX(item);
    }
	
	@Override
	public double getYValue(int series, int item) {
		return ((XYSeriesImmutable)getSeries(series)).getYY(item);
	}
}
