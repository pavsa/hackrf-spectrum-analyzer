package jspectrumanalyzer.core;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import jspectrumanalyzer.ui.GraphicsToolkit;

public class FrequencyAllocationTable {
	/**
	 * bands will be sorted in the set by frequency
	 */
	private final TreeSet<FrequencyBand> frequencyBands	= new TreeSet<>();
	private final String area;
	
	public FrequencyAllocationTable(String area, ArrayList<FrequencyBand> bands) {
		this.area	= area;
		this.frequencyBands.addAll(bands);
	}
	
	public FrequencyBand lookupBand(long hz) {
		FrequencyBand band	= frequencyBands.floor(new FrequencyBand(hz, hz, "", ""));
		return band;
	}

	@Override
	public String toString() {
		return area;
	}
	
	public ArrayList<FrequencyBand> getFrequencyBands(long startHz, long endHz){
		FrequencyBand startBand	= lookupBand(startHz);
		ArrayList<FrequencyBand> bands	= new ArrayList<>();
		SortedSet<FrequencyBand> entries = frequencyBands.tailSet(startBand);
		for (FrequencyBand frequencyBand : entries) {
			if (frequencyBand.getHzStartIncl() > endHz) {
				break;
			}
			bands.add(frequencyBand);
		}
		return bands;
	}
	private static float map(float x, float in_min, float in_max, float out_min, float out_max)
	{
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	public BufferedImage drawAllocationTable(int width, int height, float alpha, long freqStartHz, long freqEndHz, Color textColor, Color bgColor) {
		/*
		 * draw the image scaled up and then resize it after rendering producing high quality image
		 */
		int scale	= 1;
		int drawWidth	= width*scale;
		int drawHeight	= height*scale;
		BufferedImage i	= GraphicsToolkit.createAcceleratedImageTransparent(drawWidth, drawHeight);
		Graphics2D g	= i.createGraphics();

		AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(alcom);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);					
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(1f));
		//g.rotate(Math.toRadians(90));
		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(0), 0, 0);
		Font font = new Font(Font.DIALOG, Font.PLAIN, 10*scale);
		Font rotatedFont = font.deriveFont(affineTransform);
		Rectangle2D fontBounds = g.getFontMetrics(rotatedFont).getStringBounds("80M", g);
		g.setFont(rotatedFont);
		int fontHeight	= (int) fontBounds.getHeight();
		
		ArrayList<FrequencyBand> bands = getFrequencyBands(freqStartHz, freqEndHz);
		
		float freqRange	= freqEndHz-freqStartHz;
		Float rect = new Rectangle2D.Float();
		final float textOffset	= fontHeight;
		final float textX	= fontHeight/2;
		
		class BandRectangle{
			ArrayList<String> lines; /*null for no text*/;
			float x,y,w;
			FrequencyBand band;
			String longestString;
			boolean textVisible	= true;
			public BandRectangle(FrequencyBand band, ArrayList<String> lines, float x, float y, float w, String longestString) {
				this.band	= band;
				this.lines = lines;
				this.x = x;
				this.y = y;
				this.w = w;
				this.longestString	= longestString;
			}
			
		}
		
		ArrayList<BandRectangle> shapes	= new ArrayList<>(bands.size());
		for (int j = 0; j < bands.size(); j++) {
			FrequencyBand band = bands.get(j);
			float xStart 	= (band.getHzStartIncl()-freqStartHz)*drawWidth/(freqRange);
			float xEnd 	= (band.getHzEndExcl()-freqStartHz)*drawWidth/(freqRange);
			if (xStart < 0)
				xStart	= 0;//if the band starts offscreen to the left, start it on 0
			float x	= xStart;
			float y	= 0;
			float w	= xEnd-xStart;
			
			String[] lines = band.getName().split("/");
			ArrayList<String> listLines	= new ArrayList<>(lines.length+1);
			listLines.add(String.format("%d - %dM", band.getHzStartIncl()/1000000, band.getHzEndExcl()/1000000));
			for (int line = 0; line < lines.length; line++) {
				String linestr	= lines[line];
				listLines.add(linestr);
			}			
			/*
			 * find the longest string
			 */
			Arrays.sort(lines, (o1, o2) -> Integer.compare(o2.length(), o1.length()));
			
			shapes.add(new BandRectangle(band, listLines, x, y, w, lines[0]));
		}
		
		float maxVisibleLines	= 1;
		for (int j = 0; j < shapes.size(); j++) {
			BandRectangle shape	= shapes.get(j);
			/*
			 * determine if the first line will fit inside the rectangle
			 */
			Rectangle2D bounds = g.getFontMetrics(rotatedFont).getStringBounds(shape.lines.get(0), g);
			if (shape.w > bounds.getWidth()) {
				if (shape.lines.size() > maxVisibleLines)
					maxVisibleLines	= shape.lines.size();
			}
			else {
				shape.textVisible	= false;
			}
		}
		
		float rectHeight	= (maxVisibleLines) * fontHeight + fontHeight/2;
		rect.height	= rectHeight;
		for (int j = 0; j < shapes.size(); j++) {
			BandRectangle shape	= shapes.get(j);
			rect.x	= shape.x;
			rect.y	= shape.y;
			rect.width	= shape.w;
			g.setColor(bgColor);
			g.fill(rect);
			g.setColor(Color.black);
			g.draw(rect);
			if (shape.textVisible) {
				g.setColor(textColor);
//				g.setColor(Color.white);
				Graphics2D gg = (Graphics2D) g.create((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
				for (int line = 0; line < shape.lines.size(); line++) {
					String linestr	= shape.lines.get(line);
					gg.drawString(linestr, textX, textOffset + (line)*fontHeight);
				}
				gg.dispose();
			}
		}
		
		g.dispose();
		if (scale > 1) {
			BufferedImage iOut	= GraphicsToolkit.createAcceleratedImageTransparent(width, height);
			g	= iOut.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.drawImage(i, 0, 0, width, height, null);
			g.dispose();
			return iOut;
		}
		return i;
	}
	
}
