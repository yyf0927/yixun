package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by netease on 15/7/28.
 * Beifeng bwifeng@163.com
 */
public class InfoDetailBean implements Serializable {
    private static final long serialVersionUID = -1939140139961560649L;

    private int mUserId;
    private String mAvatar;
    private String mUserName;
    private String mLostName;
    private String mLostSex;
    private String mLostTime;
    private String mCreateTime;
    private String mLostPlace;
    private String mContact;
    private String mDescribes;
    private int mYear;
    private int mBouns;
    private int mCommentNum;
    private String mPhotoName;
    private float mLongitude;
    private float mLatitude;
    private List<Comment> mCommentsList;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getLostName() {
        return mLostName;
    }

    public void setLostName(String lostName) {
        this.mLostName = lostName;
    }

    public String getLostSex() {
        return mLostSex;
    }

    public void setLostSex(String lostSex) {
        this.mLostSex = lostSex;
    }

    public String getLostTime() {
        return mLostTime;
    }

    public void setLostTime(String lostTime) {
        this.mLostTime = lostTime;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        this.mCreateTime = createTime;
    }

    public String getLostPlace() {
        return mLostPlace;
    }

    public void setLostPlace(String lostPlace) {
        this.mLostPlace = lostPlace;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    public String getDescribes() {
        return mDescribes;
    }

    public void setDescribes(String describes) {
        this.mDescribes = describes;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public int getBouns() {
        return mBouns;
    }

    public void setBouns(int bouns) {
        this.mBouns = bouns;
    }

    public int getCommentNum() {
        return mCommentNum;
    }

    public void setCommentNum(int commentNum) {
        this.mCommentNum = commentNum;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        this.mPhotoName = photoName;
    }

    public List<Comment> getCommentsList() {
        return mCommentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.mCommentsList = commentsList;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float longitude) {
        this.mLongitude = longitude;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float latitude) {
        this.mLatitude = latitude;
    }
}
