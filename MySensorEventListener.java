package com.example.tiana.visualisierungaccelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Tiana on 05.11.17.
 */

public class MySensorEventListener implements android.hardware.SensorEventListener {

    private Handler handler;
    private double  counter = 0;
    private int sample = 0;

    public MySensorEventListener(Handler handler) {
        this.handler = handler;

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {

        //get the Sensor data
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        Message msg = handler.obtainMessage();
        msg.obj = new MyDataPoint(x, y, z);
        if(counter >= sample){
            counter = 0;
            handler.sendMessage(msg);
        }
        counter = counter + 1;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //throw new UnsupportedOperationException("adf");
    }


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public double getCounter() {
        return counter;
    }

    public void setCounter(double counter) {
        this.counter = counter;
    }

    public int getSample() {
        return sample;
    }

    public void setSample(int sample) {
        this.sample = sample;
    }
};