package com.netease.study.exercise.bean;

import java.io.Serializable;

/**
 * Created by zw on 16/11/11.
 * 自动登录的bean
 */
public class TokenBean implements Serializable {
    private static final long serialVersionUID = 7672791243922041748L;

    private int mIsOpen;
    private String mContact;
    private String mSign;
    private String mUserName;
    private String mPhotoName;
    private int mPoints;
    private int mUserId;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
    }

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int points) {
        this.mPoints = points;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public int getIsOpen() {
        return mIsOpen;
    }

    public void setIsOpen(int isOpen) {
        this.mIsOpen = isOpen;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        this.mSign = sign;
    }
}

