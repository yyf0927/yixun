package com.netease.study.exercise.share;

import android.support.annotation.Nullable;

/**
 * Created by tangjie on 8/24/16.
 * 分享API接口
 */
public interface ShareApi {
    enum ShareType {
        SYSTEM,
        WEIBO,
        WECHAT,
    }
    void share(@Nullable String content, @Nullable String imagePath);
}
