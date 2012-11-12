package com.dantasse.androidplotdemo;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class MainActivity extends Activity implements OnClickListener {

    private XYPlot dynamicPlot;
    SampleDynamicSeries series1 = new SampleDynamicSeries(50);
    Random random = new Random();
    SensorManager sensorManager;
    Sensor accelerometer;
    AccelerometerListener accelerometerListener;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.dynamicPlot).setOnClickListener(this);
        
        setupGraph();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accelerometerListener = new AccelerometerListener(series1, dynamicPlot);
        sensorManager.registerListener(accelerometerListener, accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    private void setupGraph() {

        dynamicPlot = (XYPlot) findViewById(R.id.dynamicPlot);
        // only display whole numbers in domain labels
        dynamicPlot.getGraphWidget().setDomainValueFormat(new DecimalFormat("0"));

        dynamicPlot.addSeries(series1,
                new LineAndPointFormatter(Color.rgb(0, 0, 0), null, Color.rgb(0, 80, 0)));
        // create a series using a formatter with some transparency applied:
        LineAndPointFormatter f1 = new LineAndPointFormatter(Color.rgb(0, 0,200), null, Color.rgb(0, 0, 80));
        f1.getFillPaint().setAlpha(220);
        dynamicPlot.setGridPadding(5, 0, 5, 0);

        dynamicPlot.setDomainStepMode(XYStepMode.SUBDIVIDE);
        dynamicPlot.setDomainStepValue(series1.size());

        // thin out domain/range tick labels so they don't overlap each other:
        dynamicPlot.setTicksPerDomainLabel(5);
        dynamicPlot.setTicksPerRangeLabel(3);
        dynamicPlot.disableAllMarkup();

        // freeze the range boundaries:
        dynamicPlot.setRangeBoundaries(-10, 10, BoundaryMode.FIXED);

    }

    @Override
    public void onClick(View v) {
        Logger.getAnonymousLogger().info("Added one");
        series1.addNumberAndRemoveFirst(10f * (random.nextFloat() - .5f));
        dynamicPlot.redraw();
    }

}
