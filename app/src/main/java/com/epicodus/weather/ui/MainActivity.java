package com.epicodus.weather.ui;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.weather.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    @Bind(R.id.cityEditText) EditText cityEditText;
    @Bind(R.id.submitButton) Button submitButton;
    @Bind(R.id.textViewStepCount) TextView counter;

    private SensorManager sensorManager;
    boolean activityRunning;
    private int mStepValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        ButterKnife.bind(this);

        mStepValue = 0;

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
        intent.putExtra("location", cityEditText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Hardware counter isn't available, utilizing custom!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            counter.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
