package com.hynson.flutterlearn.channel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.hynson.flutterlearn.Utils;

import org.jetbrains.annotations.NotNull;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * Author: Hynsonhou
 * Date: 2022/2/21 9:57
 * Description: 方法通道
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/2/21   1.0       首次创建
 */
public class MethodChannelPlugin implements MethodChannel.MethodCallHandler {
    private static final String METHOD_CHANNEL = "samples.flutter.io/battery";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @Override
    public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) {
        if (call.method.equals("getBatteryLevel")) {
            int batteryLevel = Utils.INSTANCE.getBatteryLevel(mContext);

            if (batteryLevel != -1) {
                result.success(batteryLevel);
            } else {
                result.error("UNAVAILABLE", "Battery level not available.", null);
            }
        } else {
            result.notImplemented();
        }
    }

    public static MethodChannelPlugin registerWith(FlutterEngine engine, Context context) {
        mContext = context;
        return new MethodChannelPlugin(engine);
    }

    public MethodChannelPlugin(FlutterEngine engine) {
        new MethodChannel(engine.getDartExecutor().getBinaryMessenger(), METHOD_CHANNEL).setMethodCallHandler(this);
    }
}
