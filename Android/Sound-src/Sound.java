package com.magicsoftware.magicdev;

import android.net.Uri;
import android.media.Ringtone;
import android.media.RingtoneManager;
import com.magicsoftware.core.CoreApplication;

public class Sound {
	public static void playSound() {
		try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(CoreApplication.getInstance().getApplicationContext(),notification);
                r.play();
		} catch (Exception e) {}
	}
}
