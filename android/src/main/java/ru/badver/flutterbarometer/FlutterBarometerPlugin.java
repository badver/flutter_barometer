package ru.badver.flutterbarometer;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;

import static android.content.Context.SENSOR_SERVICE;

/** FlutterBarometerPlugin */
public class FlutterBarometerPlugin implements MethodCallHandler {

  private SensorManager sensorManager;
  private Sensor barometer;
  private Registrar registrar;

  public FlutterBarometerPlugin() {

  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_barometer");
    channel.setMethodCallHandler(new FlutterBarometerPlugin(registrar));
  }

  public FlutterBarometerPlugin(Registrar registrar) {
    this.sensorManager = (SensorManager) registrar.activeContext().getSystemService(SENSOR_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
      this.barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }
  }

  double getBarometer() {

    return 79.0;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
      return;
    }

    if (call.method.equals("getBarometer")) {
      result.success(getBarometer());
      return;
    }

    result.notImplemented();

  }
}
