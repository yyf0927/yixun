package com.netease.study.exercise.utils.image;

import java.io.File;

/**
 * Created by zyl06 on 11/6/16.
 */
public interface ImageLoadListener {
    void onLoadSuccess(File resource);
    void onLoadFailed();
    void onLoadStart();
}
