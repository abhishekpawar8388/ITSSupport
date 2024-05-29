//
//  DisableBounce.m
//  MagicApp
//

#import "DisableBounce.h"
#import "Magicxpa.h"
#import <WebKit/WebKit.h>

@interface DisableBounce ()

@end

@implementation DisableBounce

+(void)disableBounce:(NSArray *)params
{
	// get a value of a magic group control and add a child control to it
	NSString *ctrlname = [params objectAtIndex: 0];
	long generation = [[params objectAtIndex:1] longValue];
	
	// find the magic control
	UIView *myCusstomView = [Magicxpa getControlByName:ctrlname TaskGeneration:generation];
	// calls a native code to create a new native control and add it to the magic control
    [self performSelectorOnMainThread:@selector(disable:) withObject:myCusstomView waitUntilDone:YES];
}

+(void)disable:(UIView *)control
{
//	for (id subview in control.subviews)
//	  if ([[subview class] isSubclassOfClass: [UIWebView class]])
//		((UIScrollView *)subview).bounces = NO;

	((WKWebView *) ([control.subviews objectAtIndex:0])).scrollView.bounces = NO;
}

@end
