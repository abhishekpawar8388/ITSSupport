package com.magicsoftware.magicdev;

import android.app.Activity;
import android.content.Intent;
import com.magicsoftware.core.CoreApplication;

public class Fingerprint {
	public static void open() {

		Intent intent = new Intent(CoreApplication.getInstance().currentActivity, FingerprintActivity.class); 
//		CoreApplication.getInstance().currentActivity.startActivity(intent);
		CoreApplication.getInstance().currentActivity.startActivityForResult(intent,123);
	}

}
