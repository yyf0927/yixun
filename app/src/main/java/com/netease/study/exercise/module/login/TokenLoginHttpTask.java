package com.netease.study.exercise.module.login;

import com.netease.study.exercise.bean.TokenBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

/**
 * Created by zw on 16/10/21.
 */
public class TokenLoginHttpTask extends FormRequest {


    public TokenLoginHttpTask() {
        super();

    }

    @Override
    public String getApi() {
        return "user/loginByToken";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return TokenBean.class;
    }
}
