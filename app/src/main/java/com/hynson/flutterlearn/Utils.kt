package com.hynson.flutterlearn

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build

/**
 * Author: Hynsonhou
 * Date: 2022/2/19 15:08
 * Description: 工具类
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/2/19   1.0       首次创建
 */
object Utils {
    fun getBatteryLevel(context: Context): Int {
        var batteryLevel = -1
        batteryLevel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(context.applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }
}