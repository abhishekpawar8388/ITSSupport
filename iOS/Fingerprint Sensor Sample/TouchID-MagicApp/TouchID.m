//
//  TouchID.m
//  MagicApp
//

// Don't forget to add the LocalAuthentication framework to the project 

#import "TouchID.h"

#import <LocalAuthentication/LocalAuthentication.h>

@interface TouchID ()

@end

@implementation TouchID

static NSString *retcode=@"";

+(NSString *) openTouchID
{
	[self performSelectorOnMainThread:@selector(openTouch) withObject:nil waitUntilDone:YES];
    return retcode;
}

+(void)openTouch
{
    // find the latest view controller
	LAContext *context = [[LAContext alloc] init];
	NSError *error = nil;
	__block NSInteger authcode = 0;

	// test if we can evaluate the policy, this test will tell us if Touch ID is available and enrolled
	if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
		// show the authentication UI with a reason string
		[context evaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics
        localizedReason:@"Are you the device owner?"
        reply:^(BOOL success, NSError *authenticationError) {
			authcode = (success) ? 1 : authenticationError.code; 
		}]; 

        while (authcode == 0) { 
            [[NSRunLoop mainRunLoop] limitDateForMode:NSDefaultRunLoopMode]; 
        } 
		if (authcode == 1) { 
			retcode = @"0"; //You are the device owner.
        } 
		else if (authcode == LAErrorUserCancel) {
			retcode = @"1"; //User pressed the Cancel button.
		}
		else if (authcode == LAErrorUserFallback) {
			retcode = @"2"; //User pressed the Enter Password button.
		}
		else if (authcode == LAErrorPasscodeNotSet) {
			retcode = @"3"; //Passcode is not set on the device.
		}
		else if (authcode == LAErrorAuthenticationFailed) {
			retcode = @"4"; //User failed to provide valid credentials.
		}
		else {
			retcode = @"5"; //There was a problem verifying your identity.
		} 
 	}
    else {
		retcode = @"6"; //TouchID is not available.
    }
}

@end
