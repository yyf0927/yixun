package com.netease.study.exercise;

import android.os.Environment;

import java.io.File;

/**
 * Created by netease on 16/8/7.
 */
public class ExerciseConst {

    public static final String MI_PUSH_APP_ID = "2882303761517503818";

    public static final String MI_PUSH_APP_KEY = "5571750355818";

    public static final String MI_PUSH_SCER = "vnT+K90PYvG+HiYsAMuNRg==";

    public static final String SINA_APP_ID = "4181920919";

    public static final String WECHAT_APP_ID = "wx1e27b6b5483d1f88";

    public static final long K = 1024;

    public static final long M = K * K;

    public static final String MAIN_PREFERENCE_FILE = "mainPreferenceFile";
    public static class PREFERENCE_KEY {
        public static final String FIRST_START_APP = "FIRST_START_APP";
        public static final String DEVICEID = "DEVICEID";
    }

    public static final int SCREEN_WIDTH = AppProfile.getContext().getResources().getDisplayMetrics().widthPixels;
    public static final int SCREEN_HEIGHT = AppProfile.getContext().getResources().getDisplayMetrics().heightPixels;

    public static final String WORK_PATH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "netease" + File.separator + "exercise";
    public static final String WORK_PATH_CACHE = WORK_PATH_ROOT + File.separator + "cache";
    public static final String WORK_PATH_CACHE_IMAGE = WORK_PATH_CACHE + File.separator + "image";
    static {
        new File(WORK_PATH_CACHE_IMAGE).mkdirs();
    }
}
