package com.netease.study.exercise.module.login;

import android.support.annotation.NonNull;

import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

/**
 * Created by zw on 16/10/21.
 */
public class LoginHttpTask extends FormRequest {


    public LoginHttpTask(@NonNull String mobile, @NonNull String pwd) {
        super();
        mBodyMap.put("mobile", mobile);
        mBodyMap.put("pwd", pwd);

    }

    @Override
    public String getApi() {
        return "user/login";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return UserBean.class;
    }
}
