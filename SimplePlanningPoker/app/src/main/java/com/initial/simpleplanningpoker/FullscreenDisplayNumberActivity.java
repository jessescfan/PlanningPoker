package com.initial.simpleplanningpoker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenDisplayNumberActivity extends AppCompatActivity {
    private View mContentView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private boolean showingScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_display_number);
        mContentView = findViewById(R.id.fullscreen_content);

        int bgColor = getRandomColor();
        mContentView.setBackgroundColor(bgColor);
        TextView text = (TextView)mContentView;
        int textColor = getRandomColor();
        text.setTextColor(textColor);

        Intent intent = new Intent(this, ShakeService.class);
        //Start Service
        startService(intent);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                showScore();
            }
        });

    }

    public void toggleScore(View view) {
        if (!showingScore) {
            showScore();
        } else {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void showScore() {
        showingScore = true;

        Intent intent = getIntent();
        String displayNumber = intent.getStringExtra("display_number");
        TextView text = (TextView)mContentView;
        if (displayNumber.toLowerCase().contains("coffee")) {
            text.setTextSize(100);
        } else {
            text.setTextSize(220);
        }
        text.setText(displayNumber);
    }

    private int getRandomColor()
    {
        Random rnd = new Random();

        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
