package com.netease.study.exercise;

import android.content.Context;

import com.netease.study.exercise.net.HttpClientWrapper;

/**
 * Created by zw on 16/10/28.
 */
public class AppProfile {
    private static Context sApplicationContext;
    private static HttpClientWrapper sHttpClientWrapper;
    private static String sXiaoMiRegId;

    public static HttpClientWrapper getHttpClientWrapper() {
        return sHttpClientWrapper;
    }

    public static Context getContext() {
        return sApplicationContext;
    }

    public static void init(Context context) {
        sApplicationContext = context;
        sHttpClientWrapper = new HttpClientWrapper(context);
    }

    public static void setXiaoMiRegId(String regId) {
        sXiaoMiRegId = regId;
    }

    public static String getXiaoMiRegId() {
        return sXiaoMiRegId;
    }
}
