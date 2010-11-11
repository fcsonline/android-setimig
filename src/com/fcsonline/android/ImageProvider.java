package com.fcsonline.android;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ImageProvider {

	private HashMap<Integer, Bitmap> naipes = new HashMap<Integer, Bitmap>();

	public ImageProvider(Bitmap bitmap, int spriteWidth, int spriteHeight) {
		super();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				
				int key  = i * 13 + j;
				int x = j * spriteWidth;
				int y = i * spriteHeight;
				
				int [] pixels = new int [spriteWidth * spriteHeight];
				bitmap.getPixels(pixels, 0, spriteWidth, x, y, spriteWidth, spriteHeight);
				
				Bitmap card = Bitmap.createBitmap(pixels, spriteWidth, spriteHeight, bitmap.getConfig());
				
				naipes.put(key, card);
			}
			
		}
		
	}
	
	public Bitmap getImage (int id) {
		return naipes.get(id);
	}
	
}
