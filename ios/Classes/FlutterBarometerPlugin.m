#import "FlutterBarometerPlugin.h"
@import CoreMotion;

@implementation FlutterBarometerPlugin

NSNumber *_pressure;
CMAltimeter *_altimeter;

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_barometer"
            binaryMessenger:[registrar messenger]];
  FlutterBarometerPlugin* instance = [[FlutterBarometerPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"initialize" isEqualToString:call.method]) {
      _altimeter = [[CMAltimeter alloc] init];
      [_altimeter startRelativeAltitudeUpdatesToQueue:NSOperationQueue.mainQueue withHandler:^(CMAltitudeData * _Nullable altitudeData, NSError * _Nullable error) {
          _pressure = altitudeData.pressure;
      }];
    result([NSNumber numberWithBool:YES]);
  } else if ([@"getBarometer" isEqualToString:call.method]) {
      result([NSNumber numberWithDouble:[_pressure doubleValue]]);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
