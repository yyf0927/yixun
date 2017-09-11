package com.netease.study.exercise.module.mylostlist;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

public class MyLostListHttpTask extends FormRequest {
    public MyLostListHttpTask() {
        super();
        mBodyMap.put("infoType", "0");
    }

    @Override
    public String getApi() {
        return "info/list";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return LostListBean.class;
    }
}
