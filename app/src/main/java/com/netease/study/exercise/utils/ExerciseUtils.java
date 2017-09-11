package com.netease.study.exercise.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.netease.study.exercise.AppProfile;
import com.sina.weibo.sdk.utils.MD5;

import java.util.List;

/**
 * Created by netease on 16/11/8.
 */

public class ExerciseUtils {
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static synchronized String getDeviceID() {

        String deviceId = PreferenceUtils.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        String serial = serialNumber();
        String sep = "\t";

        if (serial == null) {
            serial = "";
        }
        serial = serial.trim();

        deviceId = MD5.hexdigest(androidID() + sep + serial);

        if (!TextUtils.isEmpty(deviceId)) {
            PreferenceUtils.saveDeviceId(deviceId);
        }

        return deviceId;
    }

    private static String androidID() {
        return Settings.Secure.getString(AppProfile.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String serialNumber() {
        return android.os.Build.SERIAL;
    }

    private static String getPhoneIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    private static String getWifiMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();//Caused by: java.lang.NullPointerException: name == null
            return info.getMacAddress();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static void closeKeyborad(Activity activity) {
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            mInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyborad(Activity activity) {
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            mInputMethodManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.RESULT_SHOWN);
        }
    }

    public static int dp2px(float dipValue){
        return (int)(dipValue * AppProfile.getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static boolean isWeixinInstalled(Context context) {
        return isAppInstalled(context, "com.tencent.mm");
    }

    public static boolean isWeiboInstalled(Context context) {
        return isAppInstalled(context, "com.sina.weibo");
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
