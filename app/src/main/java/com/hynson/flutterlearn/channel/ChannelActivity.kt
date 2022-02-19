package com.hynson.flutterlearn.channel

import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler


class ChannelActivity : FlutterActivity() {

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler(
            object : MethodCallHandler {
                override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                    if (call.method == "getBatteryLevel") {
                        val batteryLevel: Int = getBatteryLevel()
                        if (batteryLevel != -1) {
                            result.success(batteryLevel)
                        } else {
                            result.error("UNAVAILABLE", "Battery level not available.", null)
                        }
                    } else {
                        result.notImplemented()
                    }
                }
            })
    }

    private fun getBatteryLevel(): Int {
        var batteryLevel = -1
        batteryLevel = if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }

    companion object {
        const val CHANNEL = "samples.flutter.io/battery"
    }
}