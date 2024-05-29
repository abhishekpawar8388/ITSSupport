package com.magicsoftware.magicdev;

import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.content.Context;
import com.magicsoftware.core.CoreApplication;

public class Keyboard {
	public static void openKeyboard() {
		InputMethodManager inputMethodManager=(InputMethodManager)CoreApplication.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	}
}
