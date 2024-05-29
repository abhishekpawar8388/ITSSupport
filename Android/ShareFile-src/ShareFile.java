package com.magicsoftware.magicdev;

import java.io.File;
import android.net.Uri;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import androidx.core.content.FileProvider;
import com.magicsoftware.core.CoreApplication;

public class ShareFile {
	public static void share(final String fileName, final String mimeType) {
		Intent newIntent = new Intent(android.content.Intent.ACTION_SEND); 
		newIntent.setType(mimeType.trim());
		Uri fileUri = FileProvider.getUriForFile(CoreApplication.getInstance().getApplicationContext(), CoreApplication.getInstance().getApplicationContext().getPackageName() + ".provider", new File(fileName.trim()));
		newIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		newIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
		CoreApplication.getInstance().currentActivity.startActivity(Intent.createChooser(newIntent, "Share via"));
	}
}
