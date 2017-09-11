package com.netease.study.exercise.module.lostdetail;

import com.netease.study.exercise.bean.LostDetailBean;
import com.netease.study.exercise.net.FormRequest;

import static com.netease.study.exercise.net.HttpMethod.POST;

/**
 * Created by vincent on 05/12/2016.
 */

public class GetLostDetailHttpTask extends FormRequest {
    public GetLostDetailHttpTask(int id) {
        super();
        mBodyMap.put("id", String.valueOf(id));
    }

    @Override
    public String getApi() {
        return "lost/detail";
    }

  @Override
    public int getHttpMethod() {
        return POST;
    }

    @Override
    public Class getModelClass() {
        return LostDetailBean.class;
    }
}
