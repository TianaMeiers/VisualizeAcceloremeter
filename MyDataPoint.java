package com.example.tiana.visualisierungaccelerometer;

/**
 * Created by Tiana on 05.11.17.
 */

public class MyDataPoint {

    private double x;
    private double y;
    private double z;

    public MyDataPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
