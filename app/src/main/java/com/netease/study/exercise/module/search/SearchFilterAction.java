package com.netease.study.exercise.module.search;


import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.bean.SearchFilterBean;
import com.netease.study.exercise.exception.ApiException;

import java.io.IOException;

/**
 * Created by liuqijun on 12/4/16.
 */

public class SearchFilterAction {

    public static LostListBean search(SearchFilterBean searchFilterBean, int pageSize, int pageNum) {
        SearchFilterHttpTask task = new SearchFilterHttpTask(searchFilterBean, pageSize, pageNum);
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
