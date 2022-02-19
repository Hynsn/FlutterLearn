package com.hynson.flutterlearn;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.hynson.flutterlearn.channel.ChannelActivity;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;

public class MainActivity extends FragmentActivity {

    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    private FlutterFragment flutterFragment;
    private static final String EVENT_CHANNEL = "samples.flutter.io/event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        findViewById(R.id.btn_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FlutterActivity.withCachedEngine("my_engine_id").build(MainActivity.this));
            }
        });
        findViewById(R.id.btn_channel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*// 相当于启动一个新Flutter引擎，跳转加载较慢
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.setComponent(new ComponentName(getBaseContext(), ChannelActivity.class)));*/

                EventChannelPlugin.sendEvent(Utils.INSTANCE.getBatteryLevel(getApplicationContext()));
            }
        });
        findViewById(R.id.btn_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_contain, flutterFragment, TAG_FLUTTER_FRAGMENT)
                        .commit();
            }
        });

    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        flutterFragment = (FlutterFragment) fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT);
        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.withCachedEngine("my_engine_id").build();
        }
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        flutterFragment.onPostResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flutterFragment.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        flutterFragment.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        flutterFragment.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );
    }

    @Override
    public void onUserLeaveHint() {
        flutterFragment.onUserLeaveHint();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        flutterFragment.onTrimMemory(level);
    }
}