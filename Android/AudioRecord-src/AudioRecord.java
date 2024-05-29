/* The following is from http://developer.android.com/guide/topics/media/audio-capture.html
 *
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */
package com.magicsoftware.magicdev;

import android.Manifest;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.magicsoftware.core.CoreApplication;

import java.io.IOException;

public class AudioRecord {
	
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = CoreApplication.getInstance().currentActivity.getDir("cache", 0).getAbsolutePath() + "/MyAudioMemo.3gp";
	private static MediaRecorder mRecorder = null;
	private static MediaPlayer   mPlayer = null;
	private static String finishedEventName = null;
	
	public static void startPlaying(String mFileName,String eventName) {
		finishedEventName = eventName;
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
			//raise an event when the playing is finished
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mPlayer) {
					CoreApplication.getInstance().invokeUserEvent(finishedEventName); 
				}
            });
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public static void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public static String startRecording() {
        // Request the user for permission, if it was not already granted
        boolean requestResult = CoreApplication.getInstance().requestPermission(Manifest.permission.RECORD_AUDIO);

        // The user granted the permission
        if (requestResult == true)
        {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }

            mRecorder.start();
        }
		return mFileName;
    }

    public static void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
	
}
