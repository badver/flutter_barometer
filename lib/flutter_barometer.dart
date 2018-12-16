import 'dart:async';

import 'package:flutter/services.dart';

class FlutterBarometer {
  static const MethodChannel _channel =
      const MethodChannel('flutter_barometer');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<double> get barometer async {
    final double data = await _channel.invokeMethod('getBarometer');
    return data;
  }
}
