package com.netease.study.exercise.bean;

import java.io.Serializable;

public class OtherInfoBean implements Serializable {
    private static final long serialVersionUID = -4301440515119392171L;

    private UserBean mOtherInfo;

    public UserBean getOtherInfo() {
        return mOtherInfo;
    }

    public void setOtherInfo(UserBean otherInfo) {
        this.mOtherInfo = otherInfo;
    }
}
