package com.netease.study.exercise.bean;

import java.io.Serializable;

public class LocationBean implements Serializable {
    private static final long serialVersionUID = 8536982262543091319L;

    private double mLongitude;
    private double mLatitude;

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }
}
