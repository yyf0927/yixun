package com.netease.study.exercise.module.lostlist;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.exception.ApiException;

import java.io.IOException;

public class LostListAction {
    public static LostListBean getLostList(int pageNum, int pageSize) {
        LostListHttpTask task = new LostListHttpTask(pageNum, pageSize);
        try {
            LostListBean result = ((LostListBean) task.execute().data);
            result.setPageSize(pageSize);
            result.setPageNum(pageNum);
            return result;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    public static LostListBean getLostList(int pageNum, int pageSize, double geoLng, double geoLat) {
        LostListHttpTask task = new LostListHttpTask(pageNum, pageSize, geoLng, geoLat);
        try {
            LostListBean result = ((LostListBean) task.execute().data);
            result.setPageSize(pageSize);
            result.setPageNum(pageNum);
            return result;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
