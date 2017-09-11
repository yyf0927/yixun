package com.netease.study.exercise.bean;

import java.io.Serializable;

public class LostDetailBean implements Serializable {
    private static final long serialVersionUID = 7798370935314683329L;

    private InfoDetailBean mSearchInfoDetail;

    public InfoDetailBean getSearchInfoDetail() {
        return mSearchInfoDetail;
    }

    public void setSearchInfoDetail(InfoDetailBean searchInfoDetail) {
        this.mSearchInfoDetail = searchInfoDetail;
    }
}
