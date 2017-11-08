package com.example.tiana.visualisierungaccelerometer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;

public class MainActivity extends AppCompatActivity {

    public SensorManager sensormanager;
    public Sensor accelerometer;
    public Button btnStop, btnStart, btnScreen;
    public ToggleButton togglebutton;
    public ImageView imageView;
    public View main;
    public TextView TextX, TextY, TextZ, textSamp;
    public MySensorEventListener listener, textListener;
    public SeekBar SamplingSeekBar;

    private double counter = 0;

    public GraphView graph;
    public DataModel model = new DataModel();


    final Handler updateTextHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj instanceof MyDataPoint) {
                MyDataPoint dp = (MyDataPoint) msg.obj;
                TextView tx = (TextView) findViewById(R.id.textX);
                TextView ty = (TextView) findViewById(R.id.textY);
                TextView tz = (TextView) findViewById(R.id.textZ);
                tx.setText("x:" + dp.getX());
                ty.setText("y:" + dp.getY());
                tz.setText("z:" + dp.getZ());
            }
        }
    };


    final Handler updateGraphHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj instanceof MyDataPoint) {
                MyDataPoint dp = (MyDataPoint) msg.obj;

                DataPoint xDP = new DataPoint(counter, dp.getX());
                DataPoint yDP = new DataPoint(counter, dp.getY());
                DataPoint zDP = new DataPoint(counter, dp.getZ());

                model.getSeriesX().appendData(xDP, true, 1000);
                model.getSeriesY().appendData(yDP, true, 1000, true);
                model.getSeriesZ().appendData(zDP, true, 1000, true);

                model.getSeriesX().setColor(Color.parseColor("#dd4fcc"));
                model.getSeriesY().setColor(Color.parseColor("#34b550"));
                model.getSeriesZ().setColor(Color.parseColor("#ed960b"));
                //model.getSeriesX().setThickness(5);
                //model.getSeriesY().setThickness(5);
                //model.getSeriesZ().setThickness(5);

                Viewport view = graph.getViewport();

                /*if(counter >= 20){
                    int maxX = (int) counter;
                    view.setMinX(maxX - 2);
                    view.setMaxX(maxX);
                }*/
                counter = counter + 1;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextX = (TextView) findViewById(R.id.textX);
        TextY = (TextView) findViewById(R.id.textY);
        TextZ = (TextView) findViewById(R.id.textZ);
        textSamp = (TextView) findViewById(R.id.textSamp);

        btnScreen = (Button) findViewById(R.id.btnScreen);
        togglebutton = (ToggleButton) findViewById(R.id.toggleButton);
        SamplingSeekBar = (SeekBar) findViewById(R.id.mySeek);

        listener = new MySensorEventListener(updateGraphHandler);
        textListener = new MySensorEventListener(updateTextHandler);

        graph = (GraphView) findViewById(R.id.graph);

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensormanager.registerListener(listener,
                accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensormanager.registerListener(textListener, accelerometer, SensorManager.SENSOR_DELAY_UI);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Acceleration Value");
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        graph.addSeries(model.getSeriesX());
        graph.addSeries(model.getSeriesY());
        graph.addSeries(model.getSeriesZ());

        //View Settings
        Viewport view = graph.getViewport();
        view.setMaxY(10);
        view.setMinY(-5);
        view.setMinX(0);
        view.setMaxX(10);

        view.setXAxisBoundsManual(true);
        view.setYAxisBoundsManual(false);
        view.setScalable(true);
        view.setScrollable(true);

        SamplingSeekBar.setMax(20);

        //SamplingRate live änderbar
        SamplingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int myprogress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myprogress = progress;

                //textSamp.setText( "Sampling:"+ myprogress);

                listener.setSample(myprogress);
                textListener.setSample(myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //unused
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //unused
            }
        });

        //Start + Stop Function
        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    sensormanager.unregisterListener(listener, accelerometer);
                    sensormanager.unregisterListener(textListener, accelerometer);
                    TextX.setText("x:" + "0.0");
                    TextY.setText("y:" + "0.0");
                    TextZ.setText("z:" + "0.0");
                } else {
                    sensormanager.registerListener(listener,
                            accelerometer,
                            SensorManager.SENSOR_DELAY_NORMAL);
                    sensormanager.registerListener(textListener,
                            accelerometer,
                            SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

                //Take a Screenshot and send via Email
                btnScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*//Take a Screenshot
                        Bitmap b = Screenshot.takeScreenshotOfRootView(v);
                        imageView.setImageBitmap(b);
                        main.setBackgroundColor (Color.parseColor("#999999"));
                        String temp = getIntent().getStringExtra("picture_path");
                        Uri URI = Uri.parse("file://" + temp);*/

                        // Send a Email
                        Intent messageIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        messageIntent.setType("image/*");
                        messageIntent.putExtra(Intent.EXTRA_TEXT, "Send Screenshot via Mail");
                        messageIntent.putExtra(Intent.EXTRA_TITLE, "Send Screenshot");

                        //messageIntent.putExtra(Intent.EXTRA_STREAM, URI);
                        Intent chooser = Intent.createChooser(messageIntent, "Send Image");

                        startActivity(chooser);
                    }
                });

            }


}

