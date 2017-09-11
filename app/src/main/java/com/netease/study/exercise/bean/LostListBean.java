package com.netease.study.exercise.bean;

import java.util.List;

/**
 * Created by Asha on 15/7/24.
 * Asha hzqiujiadi@corp.netease.com
 */
public class LostListBean extends PageBean {
    private static final long serialVersionUID = 1864844513850389196L;

    private List<LostListItemBean> mSearchInfos;

    public List<LostListItemBean> getSearchInfos() {
        return mSearchInfos;
    }

    public void setSearchInfos(List<LostListItemBean> searchInfos) {
        this.mSearchInfos = searchInfos;
    }

    @Override
    public List getDataset() {
        return getSearchInfos();
    }
}
