package com.hynson.flutterlearn;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

import static io.flutter.view.FlutterMain.findAppBundlePath;

public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();

    private FlutterEngine engine;
    @Override
    public void onCreate() {
        super.onCreate();

        engine = new FlutterEngine(this);
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
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
