package com.netease.study.exercise.module.lostdetail;

import com.netease.study.exercise.net.FormRequest;

import static com.netease.study.exercise.net.HttpMethod.POST;

/**
 * Created by dafei on 2017/8/26.
 */

public class CollectionHttpTask extends FormRequest {

    public CollectionHttpTask(int infoId) {
        super();

        mBodyMap.put("infoId", String.valueOf(infoId));
        mBodyMap.put("infoType", "0");
    }


    @Override
    public String getApi() {
        return "collectedInfo/add";
    }

    @Override
    public int getHttpMethod() {
        return POST;
    }

    @Override
    public Class getModelClass() {
        return null;
    }
}
