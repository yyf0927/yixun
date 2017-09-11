package com.netease.study.exercise.module.persondetail;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.net.HttpMethod;
import com.netease.study.exercise.net.MultiPartRequest;

import java.io.File;
import java.util.HashMap;

public class PersonDetailHttpTask extends MultiPartRequest {
    public PersonDetailHttpTask(@NonNull HashMap<String, String> params, String filepath) {
        super();
        mBodyMap.putAll(params);
        if (!TextUtils.isEmpty(filepath)) {
            mFiles.add(new File(filepath));
        }
    }

    @Override
    public String getApi() {
        return "user/update";
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
