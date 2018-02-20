package jspectrumanalyzer.capture;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class to capture a video of the whole JFrame into the animated GIF, while capturing only when the view updates with the new data
 */
public class ScreenCapture {
	private final JFrame frame;
	private final int width, height;
	private final ImageOutputStream output;
	private final GifSequenceWriter gif;
	private final long captureMillis;
	private final File outputFile;
	private long startCaptureTS	= 0;
	private long startedCapture	= 0;
	private long lastFrameTS	= 0;
	private int framesCaptured	= 0;
	private ExecutorService saveThread	= Executors.newSingleThreadExecutor();
	private long frameIIntervalMS;
	
	public ScreenCapture(JFrame frame, int initSecs, int captureSecs, int fps, int width, int height, File outputFile) throws FileNotFoundException, IOException {
		this.captureMillis	= captureSecs*1000L;
		this.frame	 = frame;
		this.width	= width;
		this.height	= height;
		this.outputFile	= outputFile;
		
		this.startCaptureTS	= System.currentTimeMillis()+initSecs*1000L;
		frameIIntervalMS = 1000/fps;
		frame.setSize(width, height);
		
		outputFile.delete();
		
	    output = new FileImageOutputStream(outputFile);
	    gif = new GifSequenceWriter(output, BufferedImage.TYPE_3BYTE_BGR, (int)frameIIntervalMS, true);
	}
	
	public void captureFrame() {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new IllegalStateException("Capture is NOT inside event dispatch thread!");
		}
		
		long start	= System.currentTimeMillis();
		
		if (start-lastFrameTS < frameIIntervalMS || start < startCaptureTS) {
			return;
		}
		lastFrameTS	= start;
		
		if (framesCaptured == -1)
			return;
		if (framesCaptured == 0) {
			startedCapture	= System.currentTimeMillis();
			System.out.println("Capture started...");
		}

		if (System.currentTimeMillis() - startedCapture >= captureMillis) {
			System.out.println("Capture finished... frames captured: "+framesCaptured);
			framesCaptured	= -1;
//			task.cancel();
			try {
				gif.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.exit(0);
			return;
		}
		
		framesCaptured++;
		BufferedImage capImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g	= (Graphics2D) capImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);					
		frame.paint(g);
		g.dispose();
		
		/**
		 * convert to gif in a separate thread to not slow down swing's event thread
		 */
		saveThread.submit(() -> {
			try {
				gif.writeToSequence(capImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println("time to save gif "+(System.currentTimeMillis()-start));
	}
	
	
	
}
