package com.netease.study.exercise.share;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by tangjie on 8/24/16.
 */
public final class ShareApiFactory {

    public static ShareApi createShareApi(@NonNull ShareApi.ShareType type, @NonNull Activity context) {
        ShareApi api = null;
        switch (type) {
            case SYSTEM:
                api = new SystemShareApi(context);
                break;
            case WEIBO:
                api = new WeiboShareApi(context);
                break;
            case WECHAT:
                api = new WechatShareApi(context);
                break;
            default:
                throw new RuntimeException("unsupported share type");
        }
        return api;
    }

    private ShareApiFactory() {}
}
