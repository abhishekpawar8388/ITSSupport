//
//  AudioRecord.m
//  MagicApp
//

#import "AudioRecord.h"
#import "Magicxpa.h"

@interface AudioRecord ()

@end
				  
@implementation AudioRecord

static AVAudioRecorder *_audioRecorder;
static AVAudioPlayer *_audioPlayer;
static NSString *_finishedEventName;
	
+(NSString *)startRecording
{
	//Set the audio file
    NSArray *pathComponents = [NSArray arrayWithObjects:
                               [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject],
                               @"MyAudioMemo.m4a",
                               nil];
    NSURL *soundFileURL = [NSURL fileURLWithPathComponents:pathComponents];

	// Setup audio session
    AVAudioSession *session = [AVAudioSession sharedInstance];
    [session setCategory:AVAudioSessionCategoryPlayAndRecord error:nil];
 
    // Define the recorder setting
    NSMutableDictionary *recordSetting = [[NSMutableDictionary alloc] init];
    [recordSetting setValue:[NSNumber numberWithInt:kAudioFormatMPEG4AAC] forKey:AVFormatIDKey];
    [recordSetting setValue:[NSNumber numberWithFloat:44100.0] forKey:AVSampleRateKey];
    [recordSetting setValue:[NSNumber numberWithInt: 2] forKey:AVNumberOfChannelsKey];
    
    // Initiate and prepare the recorder
	NSError *error;
    _audioRecorder = [[AVAudioRecorder alloc] initWithURL:soundFileURL settings:recordSetting error:&error];
	_audioRecorder.delegate = (id<AVAudioRecorderDelegate>)[AudioRecord class];
	
    if (error)
    {
        NSLog(@"error: %@", [error localizedDescription]);
		return @"";
    } else {
		_audioRecorder.meteringEnabled = YES;
        [_audioRecorder prepareToRecord];
		[_audioRecorder record];
		return [soundFileURL path];
	}
}

+(void)stopRecording
{
	if (_audioRecorder.recording)
	{
		[_audioRecorder stop];
		AVAudioSession *session = [AVAudioSession sharedInstance];
		int flags = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation;
		[session setActive:NO withOptions:flags error:nil];
	}
}

+(void)startPlaying:(NSArray *)params
{
    NSString *mFileName = [params objectAtIndex:0];
	_finishedEventName = [params objectAtIndex: 1];
	
	NSURL *soundFileURL = [NSURL fileURLWithPath:mFileName];
	
	// Setup audio session
    AVAudioSession *session = [AVAudioSession sharedInstance];
    [session setCategory:AVAudioSessionCategoryPlayAndRecord error:nil];
	
    NSError *error;
    _audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:soundFileURL error:&error];
	_audioPlayer.delegate = (id<AVAudioPlayerDelegate>)[AudioRecord class];
	
    if (error)
        NSLog(@"Error: %@", [error localizedDescription]);
    else
        [_audioPlayer play];
}

+(void)stopPlaying
{
	if (_audioPlayer.playing) {
       [_audioPlayer stop];
    }
}


+(void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag
{
	//raise an event when the playing is finished
	NSArray *paramstosend = [NSArray arrayWithObjects: @"",nil];
    [Magicxpa invokeUserEvent:_finishedEventName Params:paramstosend];
}

+(void)audioPlayerDecodeErrorDidOccur:(AVAudioPlayer *)player error:(NSError *)error
{
        NSLog(@"Decode Error occurred");
}

+(void)audioRecorderDidFinishRecording:(AVAudioRecorder *)recorder successfully:(BOOL)flag
{
}

+(void)audioRecorderEncodeErrorDidOccur:(AVAudioRecorder *)recorder error:(NSError *)error
{
        NSLog(@"Encode Error occurred");
}
@end
