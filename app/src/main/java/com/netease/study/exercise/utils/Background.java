package com.netease.study.exercise.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.service.PollService;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Created by tangjie on 8/23/16.
 */
public final class Background {
    private static class InstanceHolder {
        final static Background INSTANCE = new Background();
    }

    public static Background getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void poll(@NonNull Context context) {
        Intent intent = new Intent(context, PollService.class);
        context.startService(intent);
    }

    public void startMiPush(@NonNull Context context) {
        MiPushClient.registerPush(context, ExerciseConst.MI_PUSH_APP_ID, ExerciseConst.MI_PUSH_APP_KEY);
    }

    private Background() {}
}
