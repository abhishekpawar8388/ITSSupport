package com.magicsoftware.magicdev;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import com.magicsoftware.core.CoreApplication;

public class Pic2ShopActivity extends Activity {
	private static final int REQUEST_CODE_SCAN = 1405; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent("com.visionsmarts.pic2shop.SCAN");
		startActivityForResult(intent, REQUEST_CODE_SCAN); 
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SCAN) {
			if (resultCode == RESULT_OK) {
				String barcode = data.getStringExtra("BARCODE");
				String barcodeFormat = data.getStringExtra("format");
				String resultText = barcode;
				if (barcodeFormat != null) {
					resultText += " " + barcodeFormat;
				}
				CoreApplication.getInstance().invokeUserEvent("barcode",resultText);
			}
			finish();
		}
	}
 }