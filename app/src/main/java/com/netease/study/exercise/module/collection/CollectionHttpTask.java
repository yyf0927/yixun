package com.netease.study.exercise.module.collection;

import com.netease.study.exercise.bean.CollectionListBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

/**
 * Created by dafei on 2017/8/29.
 */

public class CollectionHttpTask extends FormRequest {

    public CollectionHttpTask() {
        super();
    }

    @Override
    public String getApi() {
        return "collectedInfo/list";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return CollectionListBean.class;
    }
}
