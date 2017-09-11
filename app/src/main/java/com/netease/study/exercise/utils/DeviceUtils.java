package com.netease.study.exercise.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Asha on 15-7-28.
 * Asha hzqiujiadi@corp.netease.com
 *
 * 获取设备相关内容
 */
public class DeviceUtils {
    private static boolean isGenymotion;
    private static int deviceWidthPx = -1;
    private static int deviceHeightPx = -1;
    private static float scale = 2;
    private static float scaleDensity = 2;
    private static String deviceId;

    public static void init(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        scale = dm.density;
        scaleDensity = dm.scaledDensity;
        deviceWidthPx = dm.widthPixels;
        deviceHeightPx = dm.heightPixels;
    }
    public static int getDeviceWidth(){
        return deviceWidthPx;
    }
    public static int getDeviceHeight(){
        return deviceHeightPx;
    }

    public static int dp2px(float dipValue){
        return (int)(dipValue*scale+0.5f);
    }

    public static int px2dp(float pxValue){
        return (int)(pxValue/scale+0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * scaleDensity + 0.5f);
    }

    public static boolean isGenymotion() {
        return isGenymotion;
    }

    public static void setIsGenymotion(boolean isGenymotion) {
        DeviceUtils.isGenymotion = isGenymotion;
    }

    public static void setDeviceId(String deviceId) {
        DeviceUtils.deviceId = deviceId;
    }

    public static String getDeviceId() {
        return deviceId;
    }
}
