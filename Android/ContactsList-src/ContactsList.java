package com.magicsoftware.magicdev;

import android.Manifest;
import android.net.Uri;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.util.Log;
import android.app.Activity;
import com.magicsoftware.core.CoreApplication;

public class ContactsList {

	public static String getContacts(String filter) {
		String contactID = null;
		String contactName = null;
        String contactNumber = null;
		String result = "";
		
		Log.d("MGContacts", "getContacts");

		boolean permissionGrandted = CoreApplication.getInstance().requestPermission(Manifest.permission.READ_CONTACTS);
		if (permissionGrandted)
		{
			// getting contacts ID
			Uri lookupUri;
			if (TextUtils.isEmpty(filter)) {
				lookupUri = Contacts.CONTENT_URI;
			} else {
				lookupUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,Uri.encode(filter));
			}

			Cursor cursorID = CoreApplication.getInstance().currentActivity.getContentResolver().query(lookupUri,
					new String[] {Contacts._ID, Contacts.DISPLAY_NAME},
					Contacts.IN_VISIBLE_GROUP + "=1",
					null, Contacts.DISPLAY_NAME + " ASC");

			if (cursorID.moveToFirst()) {
				//loop on all the contacts matching the criteria
				do {
					contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
					contactName = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					contactNumber = "";
					Log.d("MGContacts", "contactID: " + contactID);

					// get contacts phone numbers from the contact ID
					Cursor cursorPhone = CoreApplication.getInstance().currentActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
									ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
									ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
							new String[]{contactID},
							null);
					if (cursorPhone.moveToFirst()) {
						//loop on all the phone numbers per contact
						do {
							contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							result = result + contactID + "," + contactName + "," + contactNumber + "|";
							Log.d("MGContacts", "result: " + result);
						} while(cursorPhone.moveToNext());
					} else {
						result = result + contactID + "," + contactName + "," + "" + "|";
						Log.d("MGContacts", "result: " + result);
					}
					cursorPhone.close();
				} while(cursorID.moveToNext());
			}
			cursorID.close();
		}
		
		return result;
    }

}
