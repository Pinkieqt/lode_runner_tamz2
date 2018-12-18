package com.loderunner.game;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {
    SensorManager sensorManager;
    Sensor proximitySensor;
    Button buttonplay;
    Button buttonscore;
    Button buttonexit;
    TextView prox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        buttonplay = findViewById(R.id.butmenu);
        buttonexit = findViewById((R.id.butexit));
        buttonscore = findViewById(R.id.butscore);
        prox = findViewById(R.id.textView4);

        if(proximitySensor != null) {
            sensorManager.registerListener(proximitySensorListener,
                    proximitySensor, 2 * 1000 * 1000);
        }
    }

    // Create listener
    SensorEventListener proximitySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                prox.setText("Zakrytý");
            } else {
                prox.setText("Odkrytý");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    public void startGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showScore(View view){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        System.exit(0);
    }
}
