package com.netease.study.exercise.module.mylostlist;

import java.util.List;

/**
 * Created by Asha on 15-8-1.
 * Asha hzqiujiadi@corp.netease.com
 */

/**
 * Created by zw on 16/10/28.
 */
abstract public class PageBean {
    private int totalPage;
    private int pageNum;
    private int pageSize;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    protected abstract List getDataset();


}
