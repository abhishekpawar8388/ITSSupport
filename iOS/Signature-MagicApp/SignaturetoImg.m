//
//  SignaturetoImg.m
//  MagicApp
//

#import "SignaturetoImg.h"

@interface SignaturetoImg ()

@end

@implementation SignaturetoImg

+(NSString *)convert:(NSArray *)params
{
	NSString *message = [params objectAtIndex: 0];
	NSString *filename = [params objectAtIndex: 1];

    NSArray *encodedImg = [message componentsSeparatedByString:@","];
	NSString *result=[encodedImg objectAtIndex:1];
	// NSLog(@"%@",result);
	
	NSData *decoded = [[NSData alloc]initWithBase64EncodedString:result options:NSDataBase64DecodingIgnoreUnknownCharacters];
	UIImage *img = [UIImage imageWithData:decoded];
	
	NSData *imageData = [NSData dataWithData:UIImagePNGRepresentation(img)];
	[imageData writeToFile:filename atomically:YES];

	return @"";
}

@end
