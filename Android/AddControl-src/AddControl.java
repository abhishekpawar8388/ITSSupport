package com.magicsoftware.magicdev;

import com.magicsoftware.core.CoreApplication;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

public class AddControl {
	// get a value of a magic group control and add a child control to it
	public static void add(final String ctrlname, final int generation) {
		CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// find the magic control
				View myCustomView = CoreApplication.getInstance().getControlByName(ctrlname,generation);
				if (myCustomView != null) {
					//defines a custom control
					TextView myTextView = new TextView(CoreApplication.getInstance().getApplicationContext());
					myTextView.setText("Hello World");
					//adds the custom controls to the magic group control (panel)
					((ViewGroup) myCustomView).addView(myTextView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				}
			}
		});
	}
}
