package com.netease.study.exercise.module.persondetail;

import com.netease.study.exercise.bean.OtherInfoBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

public class UserDetailHttpTask extends FormRequest {
    public UserDetailHttpTask(long otherId) {
        super();
        mBodyMap.put("otherId", otherId + "");
    }

    @Override
    public String getApi() {
        return "user/viewOtherInfo";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return OtherInfoBean.class;
    }
}
