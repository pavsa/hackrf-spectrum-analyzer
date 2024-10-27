package jspectrumanalyzer.ui;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class GraphicsToolkit {

	public static BufferedImage createAcceleratedImageTransparent(int width, int height) {
		return createAcceleratedImage(width, height, Transparency.TRANSLUCENT);
	}
	
	public static BufferedImage createAcceleratedImageOpaque(int width, int height) {
		return createAcceleratedImage(width, height, Transparency.OPAQUE);
	}
	
	private static BufferedImage createAcceleratedImage(int width, int height, int transparency) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(width, height, transparency);
		image.setAccelerationPriority(1);
		return image;
	}
	
}
