//
//  RaiseUserEvent.m
//  MagicApp
//

#import "RaiseUserEvent.h"
#import "Magicxpa.h"

@interface RaiseUserEvent ()

@end

@implementation RaiseUserEvent

+(void)raiseEvent:(NSArray *)params
{
	// raise a user event
	// first argument is the event name
	// second..n arguments are the parameters
	NSString *eventName = [params objectAtIndex: 0];
	NSString *msg1 = [params objectAtIndex: 1];
	NSString *msg2 = [params objectAtIndex: 2];

    NSArray *paramstosend = [NSArray arrayWithObjects:msg1, msg2, nil];
    [Magicxpa invokeUserEvent:eventName Params:paramstosend];
}

@end
