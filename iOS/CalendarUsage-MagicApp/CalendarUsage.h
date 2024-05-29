//
//  CalendarUsage.h
//  MagicApp
//

#import <Foundation/Foundation.h>

@interface CalendarUsage : NSObject

+(NSString *)getCalendarList;
+(void)addEvent:(NSArray *)params;

@end

