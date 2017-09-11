package com.netease.study.exercise.module.mylostlist;

import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.exception.ApiException;

import java.io.IOException;
import java.util.List;

/**
 * Created by netease on 16/12/1.
 */

public class LostListAction {
    public static List<LostListItemBean> getMyLostList() {
        MyLostListHttpTask myLostListHttpTask = new MyLostListHttpTask();
        try {
            return ((LostListBean) myLostListHttpTask.execute().data).getSearchInfos();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    public static List<LostListItemBean> getOtherLostList(long id) {
        OtherLostListHttpTask otherLostListHttpTask = new OtherLostListHttpTask(id);
        try {
            return ((LostListBean) otherLostListHttpTask.execute().data).getSearchInfos();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
