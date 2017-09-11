package com.netease.study.exercise.bean;

import java.io.Serializable;

/**
 * Created by zw on 16/10/21.
 */
public class UserBean implements Serializable {
    private static final long serialVersionUID = -4086122154387866303L;

    private String mMobile;
    private String mToken;
    private String mImToken;
    private String mUserName;
    private String mSign;
    private String mPhotoName;
    private String mContact;
    private int mPoints;
    private int mUserId;
    private int mIsFriend;

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        this.mMobile = mobile;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
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

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public String getImToken() {
        return mImToken;
    }

    public void setImToken(String imToken) {
        this.mImToken = imToken;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        this.mSign = sign;
    }

    public int getIsFriend() {
        return mIsFriend;
    }

    public void setIsFriend(int isFriend) {
        this.mIsFriend = isFriend;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "mMobile='" + mMobile + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mImToken='" + mImToken + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mSign='" + mSign + '\'' +
                ", mPhotoName='" + mPhotoName + '\'' +
                ", mContact='" + mContact + '\'' +
                ", mPoints=" + mPoints +
                ", mUserId=" + mUserId +
                ", mIsFriend=" + mIsFriend +
                '}';
    }
}
