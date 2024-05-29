//
//  ImageResize.m
//  MagicApp
//

#import "ImageResize.h"

@interface ImageResize ()

@end

@implementation ImageResize

+(NSString *)resize:(NSArray *)params
{
	// syntax: resize:ImagePath,compressionQuality,Width,Height
	// returns: the new resized image file path
	// in compressionQuality - 0 being the maximum compression, 100 being the best quality
	// in Width and Height, value of 0 means keeping the original size
	
	NSString *imagePath = [params objectAtIndex: 0];
	CGFloat compressionQuality = [[params objectAtIndex: 1] floatValue]; 
	float Width = [[params objectAtIndex: 2] floatValue];
	float Height = [[params objectAtIndex: 3] floatValue];
	
	// load the image
	NSData *imgdata = [NSData dataWithContentsOfFile:imagePath]; 
	UIImage *image = [UIImage imageWithData:imgdata];
	// resize the image
	if(Width == 0)
		Width=image.size.width;
	if(Height == 0)
		Height=image.size.height;
	CGRect rect = CGRectMake(0.0, 0.0, Width, Height);
	UIGraphicsBeginImageContext(rect.size);
	[image drawInRect:rect];
	UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
	UIGraphicsEndImageContext();
	// compress the image
	NSData *imageData = [NSData dataWithData:UIImageJPEGRepresentation(img, compressionQuality/100)];
	// save the image to a temp file in the iOS temp folder
	NSString * tempFile = [NSString pathWithComponents:@[NSTemporaryDirectory(), @"tempfile"]];
	[imageData writeToFile:tempFile atomically:YES];                                             
	return tempFile;
}

+(NSString *)rotate:(NSArray *)params
{
	// syntax: rotate:ImagePath,angel (can also be negative)
	// returns: the new rotated image file path

	NSString *imagePath = [params objectAtIndex: 0];
	CGFloat angel = [[params objectAtIndex: 1] floatValue]; 
	
	// load the image
	NSData *imgdata = [NSData dataWithContentsOfFile:imagePath]; 
	UIImage *image = [UIImage imageWithData:imgdata];
	// Calculate the size of the rotated view's containing box for our drawing space
	UIView *rotatedViewBox = [[UIView alloc] initWithFrame:CGRectMake(0,0,image.size.width, image.size.height)];
	CGAffineTransform t = CGAffineTransformMakeRotation(angel * M_PI / 180);
	rotatedViewBox.transform = t;
	CGSize rotatedSize = rotatedViewBox.frame.size;
	// Create the bitmap context
	UIGraphicsBeginImageContext(rotatedSize);
	CGContextRef bitmap = UIGraphicsGetCurrentContext();
	// Move the origin to the middle of the image so we will rotate and scale around the center.
	CGContextTranslateCTM(bitmap, rotatedSize.width/2, rotatedSize.height/2);
	// Rotate the image context
	CGContextRotateCTM(bitmap, (angel * M_PI / 180));
	//Draw the rotated/scaled image into the context
	CGContextScaleCTM(bitmap, 1.0, -1.0);
	CGContextDrawImage(bitmap, CGRectMake(-image.size.width / 2, -image.size.height / 2, image.size.width, image.size.height), [image CGImage]);
	UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
	UIGraphicsEndImageContext();
	NSData *imageData = [NSData dataWithData:UIImageJPEGRepresentation(img, 1)];
	// save the image to a temp file in the iOS temp folder
	NSString * tempFile = [NSString pathWithComponents:@[NSTemporaryDirectory(), @"tempfile"]];
	[imageData writeToFile:tempFile atomically:YES];                                             
	return tempFile;
}

@end
