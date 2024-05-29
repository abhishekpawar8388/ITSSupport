package com.magicsoftware.magicdev;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.content.ContentResolver;
import android.content.ContentValues;
import androidx.core.app.ActivityCompat;
import com.magicsoftware.core.CoreApplication;

public class CalendarUsage {
	public static void addEvent(String calendarID, String title, String description, String place, String startDate, String endDate, int reminderMinutes) {

		boolean permissionGranted = CoreApplication.getInstance().requestPermission(Manifest.permission.WRITE_CALENDAR);
		if (permissionGranted) {
			// convert the string ID to int value
			int calID = Integer.parseInt(calendarID.replaceAll("[\\D]", ""));
			// convert the string date to long values
			long dtstart = stringToDate(startDate);
			long dtend = stringToDate(endDate);

			if (dtstart == 0 || dtend == 0)
				return;

			// add the event
			ContentResolver contentResolver = CoreApplication.getInstance().getApplicationContext().getContentResolver();
			ContentValues eventValues = new ContentValues();
			eventValues.put("calendar_id", calID);
			eventValues.put("title", title);
			eventValues.put("description", description);
			eventValues.put("eventLocation", place);
			eventValues.put("dtstart", dtstart);
			eventValues.put("dtend", dtend);
			eventValues.put("eventTimezone", "UTC/GMT");
			eventValues.put("hasAlarm", 1); // 0 for false, 1 for true
			if (ActivityCompat.checkSelfPermission(CoreApplication.getInstance().currentActivity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			Uri eventUri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, eventValues);
			if (eventUri != null) {
				// get the event ID that is the last element in the Uri
				long eventID = Long.parseLong(eventUri.getLastPathSegment());
				// add reminder to the event
				ContentValues reminderValues = new ContentValues();
				reminderValues.put("event_id", eventID);
				reminderValues.put("method", 1); // Alert Methods: Default(0), Alert(1), Email(2), SMS(3)
				reminderValues.put("minutes", reminderMinutes);
				Uri reminderUri = contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues);
			}
		}
	}

	public static long stringToDate(String stringDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date date = format.parse(stringDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String getCalendarList() {
		String tmpList = "";

		boolean permissionGranted = CoreApplication.getInstance().requestPermission(Manifest.permission.READ_CALENDAR);
		if (permissionGranted) {
			//returns a list of the calendars
			String[] projection = new String[]{
					CalendarContract.Calendars._ID,
					CalendarContract.Calendars.NAME,
					CalendarContract.Calendars.ACCOUNT_NAME,
					CalendarContract.Calendars.ACCOUNT_TYPE};
			if (ActivityCompat.checkSelfPermission(CoreApplication.getInstance().currentActivity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
				return tmpList;
			}
			Cursor calCursor = CoreApplication.getInstance().getApplicationContext().getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
					projection,
					CalendarContract.Calendars.VISIBLE + " = 1",
					null,
					CalendarContract.Calendars._ID + " ASC");
			if (calCursor.moveToFirst()) {
				do {
					long id = calCursor.getLong(0);
					String displayName = calCursor.getString(1);
					//Log.v("eyal","id="+id);
					//Log.v("eyal","name="+displayName);
					tmpList += id + "," + displayName+";";
				} while (calCursor.moveToNext());
			}
		}

		return tmpList;
	}

}