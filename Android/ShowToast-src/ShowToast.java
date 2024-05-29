package com.magicsoftware.magicdev;

import android.widget.Toast; 
import android.content.Context;
import com.magicsoftware.core.CoreApplication;

public class ShowToast {
	public static void showToast(final String str) {
		try {
			CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() { 
				@Override 
				public void run() { 
					Toast.makeText(CoreApplication.getInstance().getApplicationContext(), str, Toast.LENGTH_LONG).show(); 
				} 
			});
		} catch (Exception e) {}
	}
}



