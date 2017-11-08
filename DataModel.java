package com.example.tiana.visualisierungaccelerometer;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Tiana on 05.11.17.
 */

public class DataModel {

    public LineGraphSeries<DataPoint> seriesX;
    public LineGraphSeries<DataPoint> seriesY;
    public LineGraphSeries<DataPoint> seriesZ;

    //apply the three series x,y,z
    public DataModel() {
        this.seriesX = new LineGraphSeries<DataPoint>();
        this.seriesY = new LineGraphSeries<DataPoint>();
        this.seriesZ = new LineGraphSeries<DataPoint>();
    }

    public LineGraphSeries<DataPoint> getSeriesX() {
        return seriesX;
    }

    public void setSeriesX(LineGraphSeries<DataPoint> seriesX) {
        this.seriesX = seriesX;
    }

    public LineGraphSeries<DataPoint> getSeriesY() {
        return seriesY;
    }

    public void setSeriesY(LineGraphSeries<DataPoint> seriesY) {
        this.seriesY = seriesY;
    }

    public LineGraphSeries<DataPoint> getSeriesZ() {
        return seriesZ;
    }

    public void setSeriesZ(LineGraphSeries<DataPoint> seriesZ) {
        this.seriesZ = seriesZ;
    }
}
