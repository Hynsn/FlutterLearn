package com.hynson.flutterlearn;

import androidx.appcompat.app.AppCompatActivity;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.view.FlutterMain;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_flutter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FlutterActivity.withCachedEngine("my_engine_id").build(MainActivity.this));
            }
        });
    }
}