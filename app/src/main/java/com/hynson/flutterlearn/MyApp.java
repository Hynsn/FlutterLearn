package com.hynson.flutterlearn;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hynson.flutterlearn.channel.MessageChannelPlugin;
import com.hynson.flutterlearn.channel.EventChannelPlugin;
import com.hynson.flutterlearn.channel.MethodChannelPlugin;

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

        MethodChannelPlugin.registerWith(engine,this);

        EventChannelPlugin.registerWith(engine);

        MessageChannelPlugin.registerWith(engine);

    }

    public FlutterEngine getEngine() {
        return engine;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
