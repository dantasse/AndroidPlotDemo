package com.dantasse.androidplotdemo;

import java.util.ArrayList;
import java.util.List;

import com.androidplot.series.XYSeries;

public class SampleDynamicSeries implements XYSeries {

    private List<Float> numbers = new ArrayList<Float>();
    
    public SampleDynamicSeries(int size) {
        for (int i = 0; i < size; i++) {
            numbers.add(0.0f);
        }
    }
    
    public void addNumber(float number) {
        numbers.add(number);
    }
    public void addNumberAndRemoveFirst(float number) {
        numbers.add(number);
        numbers.remove(0);
    }
    
    @Override
    public String getTitle() {
        return "foo";
    }

    @Override
    public int size() {
        return numbers.size();
    }

    @Override
    public Number getX(int arg0) {
        return arg0;
    }

    @Override
    public Number getY(int arg0) {
        return numbers.get(arg0);
    }

}
