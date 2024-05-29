package com.magicsoftware.magicdev;

import android.Manifest;
import android.telephony.SmsManager;
import android.widget.Toast;
import com.magicsoftware.core.CoreApplication;


public class SendSMS {
   public static void sendInBackground(final String phoneNumber, final String messageBody) {
      try {
         if (CoreApplication.getInstance().requestPermission(Manifest.permission.SEND_SMS))
         {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, messageBody, null, null);

            CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  Toast.makeText(CoreApplication.getInstance().getApplicationContext(), "Message Sent",
                          Toast.LENGTH_LONG).show();
               }
            });
         }
         else
         {
            CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  Toast.makeText(CoreApplication.getInstance().getApplicationContext(), "Permission not granted",
                          Toast.LENGTH_LONG).show();
               }
            });
         }
      } catch (final Exception ex) {

         CoreApplication.getInstance().currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               Toast.makeText(CoreApplication.getInstance().getApplicationContext(),ex.getMessage().toString(),
                       Toast.LENGTH_LONG).show();
               ex.printStackTrace();
            }
         });
      }
   }
}



