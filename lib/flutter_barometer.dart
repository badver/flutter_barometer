import 'dart:async';

import 'package:flutter/services.dart';

class FlutterBarometer {
  static const MethodChannel _channel =
      const MethodChannel('flutter_barometer');

  static const EventChannel _eventChannel =
      const EventChannel("flutter_barometer_pressureStream");

  static Stream<double> _pressureStream;

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<double> get barometer async {
    final double data = await _channel.invokeMethod('getBarometer');
    return data;
  }

  static Stream<double> get pressureStream {
    _pressureStream ??=
        _eventChannel.receiveBroadcastStream().map<double>((value) => value);
    return _pressureStream;
  }

  static Future<bool> initialize() async {
    final bool data = await _channel.invokeMethod('initialize');
    return data;
  }
}
