package com.netease.study.exercise.widget;

/**
 * Created by netease on 15/7/24.
 * Beifeng bwifeng@163.com
 *
 * 功能：向外提供点击事件的接口，其中position代表的是图片的索引，即第几张图片
 */
public interface FlashViewListener {
    void onClick(int position);
}
