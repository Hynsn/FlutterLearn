package com.hynson.flutterlearn;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.hynson.flutterlearn.channel.MessageChannelPlugin;
import com.hynson.flutterlearn.channel.ChannelActivity;
import com.hynson.flutterlearn.channel.EventChannelPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.plugin.common.BasicMessageChannel;

public class MainActivity extends FragmentActivity {

    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    private FlutterFragment flutterFragment;

    private static final String TAG = "FragmentActivity";

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
                // 相当于启动一个新Flutter引擎，跳转加载较慢
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.setComponent(new ComponentName(getBaseContext(), ChannelActivity.class)));

            }
        });
        findViewById(R.id.btn_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.btn_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventChannelPlugin.sendEvent(Utils.INSTANCE.getBatteryLevel(getApplicationContext()));
            }
        });
        findViewById(R.id.btn_basemessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageChannelPlugin.send(Utils.INSTANCE.getBatteryLevel(getApplicationContext())+"", new BasicMessageChannel.Reply<String>() {
                    @Override
                    public void reply(@Nullable String reply) {
                        Log.i(TAG, "MessageChannelPlugin 收到的应答: "+reply);
                    }
                });
            }
        });
    }

    private void initFragment() {
        flutterFragment = getFragment();
        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.createDefault();
//            flutterFragment = FlutterFragment.withCachedEngine("my_engine_id").build();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_contain, flutterFragment, TAG_FLUTTER_FRAGMENT)
                    .commit();
        }
    }

    private FlutterFragment getFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        flutterFragment = (FlutterFragment) fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT);
        return flutterFragment;
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