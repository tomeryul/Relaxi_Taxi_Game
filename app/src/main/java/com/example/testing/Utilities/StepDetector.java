package com.example.testing.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.testing.Interfaces.StepCallback;

public class StepDetector {
    private Sensor sensor;
    private SensorManager sensorManager;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private StepCallback stepCallback;

    private int stepCounterX = 0;
    private int stepCounterY = 0;
    private long timestamp = 0;

    double latitude,longitude ;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }






    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 6.0 || x < -6.0) {
                if(x > 6.0) {
                    stepCallback.stepLeft();
                }
                else{
                    stepCallback.stepRight();
                }
            }
            if (y > 6.0) {
                stepCallback.stepY();
            }
        }
    }

    public int getStepsY() {
        return this.stepCounterY;
    }

    public int getStepsX() {
        return this.stepCounterX;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}

