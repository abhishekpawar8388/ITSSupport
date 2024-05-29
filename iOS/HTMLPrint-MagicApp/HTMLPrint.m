//
//  HTMLPrint.m
//  MagicApp
//
#import "HTMLPrint.h"

@interface HTMLPrint ()

@end

@implementation HTMLPrint

+(void)print:(NSArray *)params
{
	// get an HTML string and print it using AirPrint protocol
	NSString *htmlString = [params objectAtIndex: 0];
	
	// calls a native code to print the html string
    [self performSelectorOnMainThread:@selector(printHTML:) withObject:htmlString waitUntilDone:YES];
}

// refer to listing 5-2 at https://developer.apple.com/library/ios/documentation/2ddrawing/conceptual/drawingprintingios/Printing/Printing.html
+(void)printHTML:(NSString *)htmlString
{
	UIPrintInteractionController *pic = [UIPrintInteractionController sharedPrintController];
	UIPrintInfo *printInfo = [UIPrintInfo printInfo];
	printInfo.outputType = UIPrintInfoOutputGeneral;
	pic.printInfo = printInfo;
	UIMarkupTextPrintFormatter *htmlFormatter = [[UIMarkupTextPrintFormatter alloc] initWithMarkupText:htmlString];
    pic.printFormatter = htmlFormatter;
	// Wait for completion
	void (^completionHandler)(UIPrintInteractionController *, BOOL, NSError *) =
		  ^(UIPrintInteractionController *printController, BOOL completed, NSError *error)
				{
					if (!completed && error)
					{
						NSLog(@"Printing could not complete because of error: %@", error);
					}
				};
	[pic presentAnimated:YES completionHandler:completionHandler];
}

@end
