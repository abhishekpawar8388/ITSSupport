//
//  AddControl.m
//  MagicApp
//

#import "AddControl.h"
#import "Magicxpa.h"

@interface AddControl ()

@end

@implementation AddControl

+(void)add:(NSArray *)params
{
	// get a value of a magic group control and add a child control to it
	NSString *ctrlname = [params objectAtIndex: 0];
	long generation = [[params objectAtIndex:1] longValue];
	
	// find the magic control
	UIView *myCustomView = [Magicxpa getControlByName:ctrlname TaskGeneration:generation];
	// calls a native code to create a new native control and add it to the magic control
    [self performSelectorOnMainThread:@selector(addControl:) withObject:myCustomView waitUntilDone:YES];
}

+(void)addControl:(UIView *)control
{
	// defines a custom control
    UILabel *label = [[UILabel alloc] initWithFrame:control.bounds];
    [label setText:@"Hello World"];
    [label sizeToFit];
	// adds the custom controls to the magic group control
	[control addSubview:label];
}

@end
