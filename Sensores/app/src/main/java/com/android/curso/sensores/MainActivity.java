package com.android.curso.sensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView _tvAcelerometro, _tvProximidad, _tvLuz, _tvGiroscopio, _tvMagnetometro, _tvGravedad, _tvRotacion;
    private SensorManager sensorManager;
    private Sensor mAcelerometro, mProximidad, mLuz, mGiroscopio,mMagnetometro, mGravedad, mRotacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mProximidad=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLuz=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mGiroscopio=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetometro=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGravedad=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mRotacion=sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        _tvAcelerometro= findViewById(R.id.tvAcelerometro);
        _tvProximidad= findViewById(R.id.tvProximidad);
        _tvLuz= findViewById(R.id.tvLuz);
        _tvGiroscopio= findViewById(R.id.tvGiroscopio);
        _tvMagnetometro= findViewById(R.id.tvMagnetometro);
        _tvGravedad= findViewById(R.id.tvGravedad);
        _tvRotacion= findViewById(R.id.tvRotacion);
    }

    private void  SensorAcelerometro(SensorEvent sensorEvent)
    {
        float x, y, z;
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        _tvAcelerometro.setText("");
        _tvAcelerometro.append("Valor x:"+x+"\n Valor y:"+y+"\n Valor z:"+z);
    }

    private void  SensorProximidad(SensorEvent sensorEvent)
    {
        float x;
        x=sensorEvent.values[0];
        _tvProximidad.setText("Valor:"+x);
    }

    private void  SensorGiroscopio(SensorEvent sensorEvent)
    {
        float x, y, z;
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        _tvGiroscopio.setText("");
        _tvGiroscopio.append("Valor x:"+x+"\n Valor y:"+y+"\n Valor z:"+z);
    }
    private void  SensorMagnetometro(SensorEvent sensorEvent)
    {
        float x, y, z;
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        _tvMagnetometro.setText("");
        _tvMagnetometro.append("Valor x:"+x+"\n Valor y:"+y+"\n Valor z:"+z);
    }
    private void  SensorGravedad(SensorEvent sensorEvent)
    {
        float x, y, z;
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        _tvGravedad.setText("");
        _tvGravedad.append("Valor x:"+x+"\n Valor y:"+y+"\n Valor z:"+z);
    }
    private void  SensorRotacion(SensorEvent sensorEvent)
    {
        float x, y, z;
        x=sensorEvent.values[0];
        y=sensorEvent.values[1];
        z=sensorEvent.values[2];
        _tvRotacion.setText("");
        _tvRotacion.append("Valor x:"+x+"\n Valor y:"+y+"\n Valor z:"+z);
    }

    private void  SensorLuz(SensorEvent sensorEvent)
    {
        float x;
        x=sensorEvent.values[0];
        _tvLuz.setText("Valor:"+x);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mProximidad, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mLuz, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mGiroscopio, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mMagnetometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mGravedad, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mRotacion, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            SensorAcelerometro(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            SensorProximidad(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT)
        {
            SensorLuz(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            SensorGiroscopio(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            SensorMagnetometro(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_GRAVITY)
        {
            SensorGravedad(sensorEvent);
        }
        else if(sensorEvent.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR)
        {
            SensorRotacion(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
