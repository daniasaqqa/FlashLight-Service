package com.example.flashapp;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyService extends Service implements SensorEventListener {
    SensorManager sensorManager;
    CameraManager cameraManager;
    Sensor sensor;
    String camera = null;
    View flashImg;
    View flashOn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        flashImg = MainActivity.flashImg;
        flashOn = MainActivity.flashImg_on;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            camera = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Not Found Sensor", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        flashImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                flashImg.setVisibility(View.GONE);
                flashOn.setVisibility(View.VISIBLE);
                    try {
                        cameraManager.setTorchMode(camera, true);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
            }

        });
        flashOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flashOn.setVisibility(View.GONE);
                flashImg.setVisibility(View.VISIBLE);
                try {
                    cameraManager.setTorchMode(camera, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }});
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}