package com.netease.study.exercise.utils;

import android.widget.Toast;

import com.netease.study.exercise.AppProfile;

/**
 * Created by zw on 16/10/28.
 */
public class ToastWrapper {
    private static Toast toast = Toast.makeText(AppProfile.getContext(), "", Toast.LENGTH_LONG);
    private static boolean isShowing = false;
    private static String lastToastContent = "";

    private static int shortDuration = 1000;
    private static int longDuration = 2000;

    // 显示短提示
    public static void makeShortToast(String content) {
        makeToast(content, shortDuration);
    }

    // 显示短提示
    public static void makeShortToast(int stringId) {
        makeShortToast(ResourceHelper.getString(stringId));
    }

    // 显示短提示
    public static void makeShortToast(int stringId, Object... objects) {
        String content = ResourceHelper.stringFormat(stringId, objects);
        makeShortToast(content);
    }

    // 显示长提示
    public static void makeLongToast(String content) {
        makeToast(content, longDuration);
    }

    private static void makeToast(String content, int duration) {
        Toast.makeText(AppProfile.getContext(),content,duration).show();
    }

    // 显示长提示
    public static void makeLongToast(int stringId) {
        makeLongToast(ResourceHelper.getString(stringId));
    }

    // 显示长提示
    public static void makeLongToast(int stringId, Object... objects) {
        String content = ResourceHelper.stringFormat(stringId, objects);
        makeLongToast(content);
    }



}
