package com.hynson.flutterlearn;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static io.flutter.view.FlutterMain.findAppBundlePath;

public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();

    private static final String METHOD_CHANNEL = "samples.flutter.io/battery";

    private FlutterEngine engine;

    @Override
    public void onCreate() {
        super.onCreate();

        engine = new FlutterEngine(this);
        Log.i(TAG, "FlutterEngine: ");

        // 预热Flutter引擎
        //        engine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        engine.getDartExecutor().executeDartEntrypoint(new DartExecutor.DartEntrypoint(findAppBundlePath(), "main"));
        engine.addEngineLifecycleListener(new FlutterEngine.EngineLifecycleListener() {
            @Override
            public void onPreEngineRestart() {
                Log.i(TAG, "onPreEngineRestart: ");
            }

            @Override
            public void onEngineWillDestroy() {
                Log.i(TAG, "onEngineWillDestroy: ");
            }
        });
        FlutterEngineCache.getInstance()
                .put("my_engine_id", engine);
        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), METHOD_CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        if (call.method.equals("getBatteryLevel")) {
                            int batteryLevel = Utils.INSTANCE.getBatteryLevel(getApplicationContext());

                            if (batteryLevel != -1) {
                                result.success(batteryLevel);
                            } else {
                                result.error("UNAVAILABLE", "Battery level not available.", null);
                            }
                        } else {
                            result.notImplemented();
                        }
                    }
                });

        EventChannelPlugin.registerWith(engine);

    }

    public FlutterEngine getEngine() {
        return engine;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
