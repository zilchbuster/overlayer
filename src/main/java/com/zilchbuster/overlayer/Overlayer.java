package com.zilchbuster.overlayer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Morph;
import Catalano.Imaging.Filters.Resize;

@Component
public class Overlayer {
	private FastBitmap sourceImage;
	private Morph catalanoMorpher;
	private Resize resizer;
	
	public Overlayer() {
		
	}
	
	public void prepImages(String imagePath1, String imagePath2) throws IOException {
		FastBitmap image1 = new FastBitmap(imagePath1);
		FastBitmap image2 = new FastBitmap(imagePath2);
		
		int finalWidth = Math.min(image1.getWidth(), image2.getWidth());
		int finalHeight = Math.min(image1.getHeight(), image2.getHeight());
	
		this.resizeConditionally(image1, finalWidth, finalHeight);
		this.resizeConditionally(image2, finalWidth, finalHeight);
			
		this.sourceImage = image2;
		this.catalanoMorpher = new Morph(image1);
	}
	
	private FastBitmap resizeConditionally(FastBitmap source, int finalWidth, int finalHeight) {
		if(source.getWidth() != finalWidth && source.getHeight() != finalHeight) {
			if(this.resizer == null)
				this.resizer = new Resize(finalWidth, finalHeight);
			this.resizer.applyInPlace(source);
		}
		return source;
	}
	
	public void overlayOnce(double sourcePercent, String outputPath) throws IOException{
		this.catalanoMorpher.setSourcePercent(sourcePercent);
	    this.catalanoMorpher.applyInPlace(this.sourceImage);	
	    this.sourceImage.saveAsJPG(outputPath);
	}
}
