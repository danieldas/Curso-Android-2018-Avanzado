package com.android.curso.sensorespractico;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sGravedad, sLuz, sRotacion;
    private ShakeDetector shakeDetector;
    LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout= (LinearLayout) findViewById(R.id.milayout);
        textView= (TextView) findViewById(R.id.texto);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sGravedad=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sLuz=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sRotacion=sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        ShakeOptions opciones= new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(2)
                .sensibility(2.0f)
                ;
        this.shakeDetector= new ShakeDetector(opciones).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                textView.setText("FUNCIONAAAA!!!");
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_GRAVITY)
        {
            float z=sensorEvent.values[2];
            if (z<-8)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT)
        {
            float valor=sensorEvent.values[0];
            if (valor<=1)
            {
                linearLayout.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
            }
            else {
                linearLayout.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
            }
        }

        else if(sensorEvent.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR)
        {
            float valor=sensorEvent.values[1];
            if (valor<=-0.5 || valor>=0.3)
            {
                setTitle("Landscape");
            }
            else {
                setTitle("Portrait");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sGravedad, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sLuz, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sRotacion, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        shakeDetector.destroy(getBaseContext());
        super.onDestroy();
    }
}
