package com.netease.study.exercise.module.persondetail;

import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

public class FriendOperationHttpTask extends FormRequest {
    private boolean mIsFollow;

    public FriendOperationHttpTask(long otherId, boolean isFollow) {
        super();
        mBodyMap.put("relationId", otherId + "");
        mIsFollow = isFollow;
    }

    @Override
    public String getApi() {
        return mIsFollow ? "relation/add" : "relation/del";
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Class getModelClass() {
        return null;
    }
}
