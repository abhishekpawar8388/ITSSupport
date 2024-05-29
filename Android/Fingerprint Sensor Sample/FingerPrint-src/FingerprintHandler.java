package com.magicsoftware.magicdev;
 
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.TextView;
import android.content.Intent;
//import com.magicsoftware.core.CoreApplication;
 
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
  
   private Context context;
  
   // Constructor
   public FingerprintHandler(Context mContext) {
       context = mContext;
   }
  
   public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
       CancellationSignal cancellationSignal = new CancellationSignal();
       if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
           return;
       }
       manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
   }
  
   @Override
   public void onAuthenticationError(int errMsgId, CharSequence errString) {
       this.update("Fingerprint Authentication error\n" + errString, false);
   }
 
   @Override
   public void onAuthenticationFailed() {
       this.update("Fingerprint Authentication failed.", false);
   }
  
   @Override
   public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
       this.update("Fingerprint Authentication succeeded.", true);
   }
 
 
   public void update(String e, Boolean success){
       TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
       textView.setText(e);
       if(success){
           textView.setTextColor(0x001e282d);
		   //close the authentication activity 
		   Intent resultIntent = ((Activity)context).getIntent();
		   resultIntent.putExtra("result", "0"); 
		   ((Activity)context).setResult(Activity.RESULT_OK, resultIntent);
		   ((Activity)context).finish();
		   //raise an event in the magic app with "0" as the parameter.
           //CoreApplication.getInstance().invokeUserEvent("Authenticationresult","0");
       }
   }
}