//
//  CalendarUsage.m
//  MagicApp
//
// requires adding EventKit framework
//
#import "CalendarUsage.h"
#import <EventKit/EventKit.h>

static EKEventStore *eventStore = nil;
//static NSString *calendarList = @"";

@implementation CalendarUsage

+(void)addEvent:(NSArray *)params
{
	NSString *calendarID = [[params objectAtIndex: 0] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
	
	NSString *title = [[params objectAtIndex: 1] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
	NSString *description = [params objectAtIndex: 2];
	NSString *place = [[params objectAtIndex: 3] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
	NSDate *startDate = [self stringToDate:[params objectAtIndex: 4]];
	NSDate *endDate = [self stringToDate:[params objectAtIndex: 5]];
	int reminderMinutes = [[params objectAtIndex: 6] intValue];

	[self requestAccess:^(BOOL granted, NSError *error) {
		if (granted) {
			EKEvent *event = [EKEvent eventWithEventStore:eventStore];
			EKCalendar *calendar = nil;

			// the following fails on iOS8
			//calendar = [eventStore calendarWithIdentifier:calendarID];
			//so instead of getting calendar by identifier, get all calendars and check matching in the cycle
			for (EKCalendar *aCal in [eventStore calendarsForEntityType:EKEntityTypeEvent]) { 
				if ([aCal.calendarIdentifier isEqualToString:calendarID]) { 
					calendar=aCal;
					break;					
				} 
			}
		
			if (!calendar) {
				NSLog(@"Error accessing calendar");
			}
			event.calendar = calendar;
			event.location = place;
			event.title = title;
			event.notes = description;
			event.startDate = startDate;
			event.endDate = endDate;
			EKAlarm *alarm = [EKAlarm alarmWithRelativeOffset:-60*reminderMinutes];
			event.alarms = [NSArray arrayWithObject:alarm];
        			
			// save event to the calendar
			NSError *error = nil;
			BOOL result = [eventStore saveEvent:event span:EKSpanThisEvent commit:YES error:&error];
			if (result) {
				// added to calendar
			} else {
				NSLog(@"Error saving event: %@", error);
			}
		} else {
			NSLog(@"you don't have permissions to access calendars");
		}
	}];
}

+ (void)requestAccess:(void (^)(BOOL granted, NSError *error))callback;
{
    if (eventStore == nil) {
        eventStore = [[EKEventStore alloc] init];
    }
    // request permissions
    [eventStore requestAccessToEntityType:EKEntityTypeEvent completion:callback];
}

+(NSDate *)stringToDate:(NSString *)stringDate 
{
	NSDateFormatter *df = [[NSDateFormatter alloc] init];
	[df setDateFormat:@"dd/MM/yyyy HH:mm:ss"];
	NSDate *myDate = [df dateFromString: stringDate];
	return myDate;
}	

+(NSString *) getCalendarList
{
	NSString *calendarList = @"";
    __block NSInteger retcode = 0;
	[self requestAccess:^(BOOL granted, NSError *error) {
		retcode = (granted) ? 1 : 2; 
	}];
	//wait for the async block to finish
    while (retcode==0) {
        [[NSRunLoop mainRunLoop] limitDateForMode:NSDefaultRunLoopMode];
    }
	if (retcode==1) {
		NSArray *calendars = [eventStore calendarsForEntityType:EKEntityTypeEvent];
		for (EKCalendar *calendar in calendars) {
			NSString *calendarIdentifier = [calendar calendarIdentifier];
			NSString *calendarTitle = [calendar title];
			//NSLog(@"Calendar = %@", calendarIdentifier);
			//NSLog(@"Calendar = %@", calendarTitle);
			calendarList = [NSString stringWithFormat:@"%@%@,%@;", calendarList, calendarIdentifier, calendarTitle];
		}
	} else {
		NSLog(@"you don't have permissions to access calendars");
	}
	return calendarList;
}

@end

