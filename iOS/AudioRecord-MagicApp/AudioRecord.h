//
//  AudioRecord.h
//  MagicApp
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@interface AudioRecord : NSObject 	<AVAudioRecorderDelegate, AVAudioPlayerDelegate>
+(NSString *)startRecording;
+(void)stopRecording;
+(void)startPlaying:(NSArray *)params;
+(void)stopPlaying;

@end
