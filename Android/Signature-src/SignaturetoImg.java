package com.magicsoftware.magicdev;

import com.magicsoftware.core.CoreApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class SignaturetoImg {
	public static String convert(String message, String fileName) {
//		System.out.println(message.toString());
		String[] encodedImg = message.split(",");
		String result=encodedImg[1].trim();
//		System.out.println(result.toString());
		
		byte[] decoded = Base64.decode(result, Base64.DEFAULT);
		Bitmap img = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
		if (img==null) {
			return ("Invalid image");
		}
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			img.compress(Bitmap.CompressFormat.PNG, 0, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			return ("File not found");
		} catch (IOException e) {
			return ("IO Exception");
		}
		return("");
	}
}
