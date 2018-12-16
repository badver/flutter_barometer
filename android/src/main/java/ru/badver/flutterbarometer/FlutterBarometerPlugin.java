package ru.badver.flutterbarometer;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/** FlutterBarometerPlugin */
public class FlutterBarometerPlugin implements MethodCallHandler, SensorEventListener {

  private SensorManager sensorManager;
  private Sensor barometer;
  private Registrar registrar;
  private float lastReading = 0.0f;
  private boolean initialized = false;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_barometer");
    channel.setMethodCallHandler(new FlutterBarometerPlugin(registrar));
  }

  public FlutterBarometerPlugin(Registrar registrar) {
    this.registrar = registrar;
  }

  boolean initializeBarometer() {
    if (!initialized) {
      this.sensorManager = (SensorManager) registrar.activeContext().getSystemService(SENSOR_SERVICE);
      this.barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
      sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
      initialized = true;
    }
    return initialized;
  }

  double getBarometer() {
    if(!initialized) throw new RuntimeException("Barometer is not initalized. You must call Barometer.initialize() before getting data");
    return lastReading;
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

    if (call.method.equals("initialize")) {
      result.success(initializeBarometer());
      return;
    }

    result.notImplemented();
  }

  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    lastReading = sensorEvent.values[0];
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {

  }
}
