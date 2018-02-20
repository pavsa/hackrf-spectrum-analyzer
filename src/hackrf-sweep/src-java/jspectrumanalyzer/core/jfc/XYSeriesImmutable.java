package jspectrumanalyzer.core.jfc;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

/**
 * Optimized immutable {@link XYSeries} for use with {@link XYLineAndShapeRenderer}.
 * No allocation of {@link XYDataItem} or any other objects.
 */
public class XYSeriesImmutable extends XYSeries {
	private float[] xValues;
	private float[] yValues;
	
	public XYSeriesImmutable(Comparable key, float[] xValues, float[] yValues) {
		super(key, false, false);
		if (xValues.length != yValues.length)
			throw new IllegalArgumentException("x/y values are not of the same size");
		this.xValues	= xValues.clone();
		this.yValues	= yValues.clone();
	}
	
	public double getXX(int item) {
		return xValues[item];
	}
	
	public double getYY(int item) {
		return yValues[item];
	}
	
	@Override
	public int getItemCount() {
		return xValues.length;
	}
	
	@Override
	public XYDataItem getDataItem(int index) {
		return null;
	}
	
}
