package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

public class FriendsListBean implements Serializable {
    private static final long serialVersionUID = 7431799437880984663L;

    private List<Friend> mRelation;

    public List<Friend> getRelation() {
        return mRelation;
    }

    public void setRelation(List<Friend> relation) {
        this.mRelation = relation;
    }

}
