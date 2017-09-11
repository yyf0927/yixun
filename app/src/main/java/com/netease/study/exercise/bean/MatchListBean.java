package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

public class MatchListBean implements Serializable {
    private static final long serialVersionUID = -7766794830731626733L;

    private List<MatchBean> mMatchInfos;
    private int mPushNum;
    private List<LocationBean> mLocations;
    double mLongitude;
    double mLatitude;

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

    public List<MatchBean> getMatchInfos() {
        return mMatchInfos;
    }

    public void setMatchInfos(List<MatchBean> matchInfos) {
        this.mMatchInfos = matchInfos;
    }

    public int getPushNum() {
        return mPushNum;
    }

    public void setPushNum(int pushNum) {
        this.mPushNum = pushNum;
    }

    public List<LocationBean> getLocations() {
        return mLocations;
    }

    public void setLocations(List<LocationBean> locations) {
        this.mLocations = locations;
    }
}
