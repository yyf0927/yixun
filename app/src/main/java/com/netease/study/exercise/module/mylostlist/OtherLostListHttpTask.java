package com.netease.study.exercise.module.mylostlist;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

public class OtherLostListHttpTask extends FormRequest {
    public OtherLostListHttpTask(long id) {
        super();
        mBodyMap.put("infoType", "0");
        mBodyMap.put("otherId", id + "");
    }

    @Override
    public String getApi() {
        return "user/listOtherPublishedInfo";
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
