package com.netease.study.exercise.bean;

import java.io.Serializable;

/**
 * Created by Asha on 15-8-6.
 * Asha ashqalcn@gmail.com
 */
public class SearchFilterBean implements Serializable {
    private static final long serialVersionUID = 6835924768087275444L;

    private int mType = 0;
    //0是男，1是女
    private int mSexType = 0;
    private String mLostProvinceAndCity;
    private String mLostYearAndMonth;
    private String mStartYear;
    private String mEndYear;

    public int getSexType() {
        return mSexType;
    }

    public void setSexType(int sexType) {
        this.mSexType = sexType;
    }

    public String getLostProvinceAndCity() {
        return mLostProvinceAndCity;
    }

    public void setLostProvinceAndCity(String lostProvinceAndCity) {
        this.mLostProvinceAndCity = lostProvinceAndCity;
    }

    public String getLostYearAndMonth() {
        return mLostYearAndMonth;
    }

    public void setLostYearAndMonth(String lostYearAndMonth) {
        this.mLostYearAndMonth = lostYearAndMonth;
    }

    public String getStartYear() {
        return mStartYear;
    }

    public void setStartYear(String startYear) {
        this.mStartYear = startYear;
    }

    public String getEndYear() {
        return mEndYear;
    }

    public void setEndYear(String endYear) {
        this.mEndYear = endYear;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
