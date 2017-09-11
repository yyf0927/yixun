package com.netease.study.exercise.bean;

import java.io.Serializable;

public class MatchBean implements Serializable {
    private static final long serialVersionUID = 6260093125699603521L;

    private int mId;
    private String mPhotoName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
    }
}
