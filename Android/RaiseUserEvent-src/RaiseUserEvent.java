package com.magicsoftware.magicdev;

import com.magicsoftware.core.CoreApplication;

public class RaiseUserEvent {
	// raise a user event
	// first argument is the event name
	// second..n arguments are the parameters
	public static void raiseEvent(String eventName, String msg1, String msg2) {
		CoreApplication.getInstance().invokeUserEvent(eventName,msg1,msg2); 
	}
}
