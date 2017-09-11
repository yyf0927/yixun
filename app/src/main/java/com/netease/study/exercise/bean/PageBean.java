package com.netease.study.exercise.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Asha on 15-8-1.
 * Asha hzqiujiadi@corp.netease.com
 */
abstract public class PageBean implements Serializable {
    private static final long serialVersionUID = -5116513119603252561L;

    private int mTotalPage;
    private int mPageNum;
    private int mPageSize;

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(int totalPage) {
        this.mTotalPage = totalPage;
    }

    public int getPageNum() {
        return mPageNum;
    }

    public void setPageNum(int pageNum) {
        this.mPageNum = pageNum;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    public abstract List getDataset();
}
