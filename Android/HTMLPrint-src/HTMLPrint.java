package com.magicsoftware.magicdev;

import com.magicsoftware.core.CoreApplication;
import android.app.Activity;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.print.PrintJob;
import android.content.Context;

// from: http://developer.android.com/training/printing/html-docs.html

public class HTMLPrint {
	// get an HTML string and print it
	public static void print(final String htmlString) {
		//This code is supported starting Android 4.4 (API 19)
		if (android.os.Build.VERSION.SDK_INT < 19) {
            return ;
        }else{
			CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					WebView mWebView;
					// Create a WebView object specifically for printing
					final WebView webView = new WebView(CoreApplication.getInstance().currentActivity);
					webView.setWebViewClient(new WebViewClient() {
						public boolean shouldOverrideUrlLoading(WebView view, String url) {
							return false;
						}
						@Override
						public void onPageFinished(WebView view, String url) {
							PrintManager printManager = (PrintManager) CoreApplication.getInstance().currentActivity.getSystemService(CoreApplication.getInstance().getApplicationContext().PRINT_SERVICE);
							PrintDocumentAdapter printAdapter = view.createPrintDocumentAdapter();
							String jobName = "Document";
							printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
							// mWebView = null;
						}
					});
					webView.loadDataWithBaseURL(null, htmlString, "text/HTML", "UTF-8", null);
					// Keep a reference to WebView object until you pass the PrintDocumentAdapter to the PrintManager
					mWebView = webView;
				}
			});
		}
	}
}
