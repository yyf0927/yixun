package com.netease.study.exercise.module.friend;

import com.netease.study.exercise.bean.FriendsListBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

/**
 * Created by netease on 16/12/2.
 */

public class GetFriendsHttpTask extends FormRequest {
    public GetFriendsHttpTask() {
        super();
    }

    @Override
    public String getApi() {
        return "relation/list";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return FriendsListBean.class;
    }
}
