package jspectrumanalyzer.ui;

import java.awt.Color;

public interface ColorPalette
{
	/**
	 * Gets color at index
	 * @param i
	 * @return
	 */
	public Color getColor(int i);
	/**
	 * Gets color normalized
	 * @param value 0-1
	 * @return
	 */
	public Color getColorNormalized(double value);
	/**
	 * Returns number of colors in the palette
	 * @return
	 */
	public int size();
}
