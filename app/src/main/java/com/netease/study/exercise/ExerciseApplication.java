package com.netease.study.exercise;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netease.nis.bugrpt.CrashHandler;
import com.netease.study.exercise.activity.imagepick.MultiImagesPreviewActivity;
import com.netease.study.exercise.activity.imagepick.SingleImagePreviewActivity;
import com.netease.hearttouch.htimagepicker.core.HTImagePicker;
import com.netease.hearttouch.htimagepicker.core.uiconfig.HTDefaultUIConfig;
import com.netease.study.exercise.activity.imagepick.ImagePickActivity;
import com.netease.study.exercise.service.PollService;
import com.netease.study.exercise.utils.Background;
import com.netease.study.exercise.utils.ThreadUtils;

import java.util.List;

/**
 * Created by netease on 16/8/7.
 */
public class ExerciseApplication extends Application {
    private static ExerciseApplication instance;
    private static final int POLL_INTERVAL = 1 * 60 * 1000;

    private PollService.OnPollResultListener mListener;

    public ExerciseApplication() {
        instance = this;
    }

    public void onCreate() {
        super.onCreate();
        if (!shouldInit()) {
            return;
        }
        CrashHandler.init(getApplicationContext());
        AppProfile.init(this);
        PollService.addListener(getPollListener());
        Background.getInstance().poll(instance);
        Background.getInstance().startMiPush(instance);
        // 图片选择器初始化
        HTDefaultUIConfig uiConfig = HTDefaultUIConfig.newBuilder()
                .setImagePickerActivityClass(ImagePickActivity.class)
                .setMultiImagePreviewActivityClazz(MultiImagesPreviewActivity.class)
                .setSingleImagePreviewActivityClass(SingleImagePreviewActivity.class)
                .setUseSystemCamera(true)
                .build();
        HTImagePicker.INSTANCE.init(this, uiConfig);
    }


    private PollService.OnPollResultListener getPollListener() {
        if (null == mListener) {
            mListener = new PollService.OnPollResultListener() {
                @Override
                public void onPollResult(@Nullable String result) {
                    Log.i(ExerciseApplication.class.getSimpleName(), "the poll result is " + result);
                    // 偷个懒,用handler意思一下.
                    ThreadUtils.runOnMainThreadDelay(new Runnable() {
                        @Override
                        public void run() {
                            Background.getInstance().poll(instance);
                        }
                    }, POLL_INTERVAL);
                }
            };
        }
        return mListener;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
