package com.netease.study.exercise.module.collection;

import com.netease.study.exercise.bean.Collection;
import com.netease.study.exercise.bean.CollectionListBean;
import com.netease.study.exercise.exception.ApiException;

import java.io.IOException;
import java.util.List;

/**
 * Created by dafei on 2017/8/29.
 */

public class CollectionAction {
    public static List<Collection> getCollections() {
        try {
            CollectionListBean listBean=((CollectionListBean) new CollectionHttpTask().execute().data);
            return listBean.getSearchInfos();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
