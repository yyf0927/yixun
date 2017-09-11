package com.netease.study.exercise.bean;

import java.io.Serializable;

public class Friend implements Serializable {
    private static final long serialVersionUID = 5675652704970042433L;

    private long mUserId;
    private String mUserName;
    private String mPhotoUrl;

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.mPhotoUrl = photoUrl;
    }
}
