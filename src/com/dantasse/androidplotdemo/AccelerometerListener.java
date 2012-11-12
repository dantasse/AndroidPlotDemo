package com.dantasse.androidplotdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.androidplot.xy.XYPlot;

public class AccelerometerListener implements SensorEventListener {

    private SampleDynamicSeries outputSeries;
    private XYPlot dynamicPlot;

    /** Takes data from the sensors, outputs it to |outputSeries| */
    public AccelerometerListener(SampleDynamicSeries outputSeries,
            XYPlot dynamicPlot) {
        this.outputSeries = outputSeries;
        this.dynamicPlot = dynamicPlot;
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    float movingAverage = 0.0f;
    @Override
    public void onSensorChanged(SensorEvent event) {
        float z = event.values[2];
        movingAverage = movingAverage * .5f + z * .5f;
        float output = z - movingAverage;
        outputSeries.addNumberAndRemoveFirst(output);
        dynamicPlot.redraw();
    }
}
