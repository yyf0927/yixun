package com.netease.study.exercise.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {
    private static final long serialVersionUID = 8018477666368828645L;

    private double mLatitude;
    private double mLongitude;
    private String mTitle;
    private String mCity;
    private String mProvince;

    public String getFormatAddress() {
        return String.format("%s%s%s", getProvince(), getCity(), getTitle());
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        this.mProvince = province;
    }
}
