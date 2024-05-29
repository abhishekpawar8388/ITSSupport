package com.magicsoftware.magicdev;

import android.content.Intent;
import com.magicsoftware.core.CoreApplication;

public class BarcodePic2Shop {
	public static void open() {
		Intent intent = new Intent(CoreApplication.getInstance().currentActivity, Pic2ShopActivity.class); 
		CoreApplication.getInstance().currentActivity.startActivity(intent);  
	}
}