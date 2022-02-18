package com.hynson.flutterlearn.channel

import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hynson.flutterlearn.MainActivity
import com.hynson.flutterlearn.R
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler


class ChannelActivity : FlutterActivity() {
    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_channel)

        initFragment()

    }

    private fun initFragment() {
//        flutterFragment = fragmentManager
//            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
//        if (flutterFragment == null) {
//            flutterFragment = FlutterFragment.withCachedEngine("my_engine_id").build<FlutterFragment>()
//        }
//        fragmentManager.beginTransaction()
//            .replace(R.id.fl_contain, flutterFragment, TAG_FLUTTER_FRAGMENT)
//            .commit()
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
//        super.configureFlutterEngine(flutterEngine)
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
        const val TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    }
}