package com.netease.study.exercise.module.lostlist;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

class LostListHttpTask extends FormRequest {
    LostListHttpTask(int pageNum, int pageSize) {
        super();

        setupPageParams(pageNum, pageSize);
    }

    LostListHttpTask(int pageNum, int pageSize, double geoLng, double geoLat) {
        super();

        setupPageParams(pageNum, pageSize);
        setupGeoParams(geoLng, geoLat);
    }

    private void setupPageParams(int pageNum, int pageSize) {
        mBodyMap.put("pageNum", String.valueOf(pageNum));
        mBodyMap.put("pageSize", String.valueOf(pageSize));
    }

    private void setupGeoParams(double geoLng, double geoLat) {
        mBodyMap.put("longitude", String.valueOf(geoLng));
        mBodyMap.put("latitude", String.valueOf(geoLat));
    }

    @Override
    public String getApi() {
        return "lost/list";
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
