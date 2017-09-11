package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dafei on 2017/8/29.
 */

public class CollectionListBean implements Serializable {
    private static final long serialVersionUID = 7431799437880984663L;
    private List<Collection> searchInfos;

    public List<Collection> getSearchInfos() {
        return searchInfos;
    }

    public void setSearchInfos(List<Collection> searchInfos) {
        this.searchInfos = searchInfos;
    }

}
