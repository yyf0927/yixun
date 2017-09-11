package com.netease.study.exercise.module.search;

import android.text.TextUtils;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.bean.SearchFilterBean;
import com.netease.study.exercise.net.FormRequest;
import com.netease.study.exercise.net.HttpMethod;

/**
 * Created by liuqijun on 12/4/16.
 */

public class SearchFilterHttpTask extends FormRequest {

    SearchFilterHttpTask(SearchFilterBean searchFilterBean, int pageSize, int pageNum) {
        super();
        mBodyMap.put("pageSize", String.valueOf(pageSize));
        mBodyMap.put("pageNum", String.valueOf(pageNum));
        mBodyMap.put("type", "0");

        mBodyMap.put("sexType", String.valueOf(searchFilterBean.getSexType()));

        if (!TextUtils.isEmpty(searchFilterBean.getLostProvinceAndCity()))
            mBodyMap.put("lostProvinceAndCity", searchFilterBean.getLostProvinceAndCity());
        if (!TextUtils.isEmpty(searchFilterBean.getLostYearAndMonth()))
            mBodyMap.put("lostYearAndMonth", searchFilterBean.getLostYearAndMonth());
        if (!TextUtils.isEmpty(searchFilterBean.getStartYear()))
            mBodyMap.put("startYear", searchFilterBean.getStartYear());
        if (!TextUtils.isEmpty(searchFilterBean.getEndYear()))
            mBodyMap.put("endYear", searchFilterBean.getEndYear());
    }


    @Override
    public String getApi() {
        return "info/filter";
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
