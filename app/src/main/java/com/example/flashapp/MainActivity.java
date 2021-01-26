package com.example.flashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static  View flashImg;
    static  View flashImg_on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this , MyService.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        flashImg = findViewById(R.id.img_flashlight_off);
        flashImg_on  = findViewById(R.id.img_flashlight_light);
    }
}
