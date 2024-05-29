package com.magicsoftware.magicdev;

import android.graphics.Matrix;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import android.content.Context;
import com.magicsoftware.core.CoreApplication;

public class ImageResize {
	public static String resize(String imagePath,int compressionQuality,int Width,int Height) {
		// syntax: resize:ImagePath,compressionQuality,Width,Height
		// returns: the new resized image file path
		// in compressionQuality - 0 being the maximum compression, 100 being the best quality
		// in Width and Height, value of 0 means keeping the original size
	 
		// load the image
		Bitmap image = BitmapFactory.decodeFile(imagePath);
		// resize the image
		if(Width == 0)
			Width=image.getWidth();
		if(Height == 0)
			Height=image.getHeight();
		Bitmap resizedImage = Bitmap.createScaledBitmap(image, Width, Height, true);
		// compress the image
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		resizedImage.compress(Bitmap.CompressFormat.JPEG, compressionQuality, blob);
		// resizedImage.compress(Bitmap.CompressFormat.PNG, 0, blob); //compression quality is ignored for png 
		// save the image to a temp file in the iOS temp folder
		File tempFile = new File(CoreApplication.getInstance().getApplicationContext().getCacheDir(),"tempfile");
		try {
			FileOutputStream fos = new FileOutputStream (tempFile); 
			blob.writeTo(fos);
			fos.close();
		}
		catch(IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
		return tempFile.getAbsolutePath();
	}
	
	public static String rotate(String imagePath,int angel) {
		// syntax: rotate:ImagePath,angel (can also be negative)
		// returns: the new rotated image file path
	 
		// load the image
		Bitmap image = BitmapFactory.decodeFile(imagePath);
        // create a matrix object
        Matrix matrix = new Matrix();
        matrix.postRotate(angel); 
        // create a new bitmap from the original using the matrix to transform the result
        Bitmap rotatedImage = Bitmap.createBitmap(image , 0, 0, image.getWidth(), image.getHeight(), matrix, true);
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		rotatedImage.compress(Bitmap.CompressFormat.PNG, 0, blob); 
		// save the image to a temp file in the iOS temp folder
		File tempFile = new File(CoreApplication.getInstance().getApplicationContext().getCacheDir(),"tempfile");
		try {
			FileOutputStream fos = new FileOutputStream (tempFile); 
			blob.writeTo(fos);
			fos.close();
		}
		catch(IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
		return tempFile.getAbsolutePath();
	}
}