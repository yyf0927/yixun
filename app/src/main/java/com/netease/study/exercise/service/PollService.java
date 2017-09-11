package com.netease.study.exercise.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.netease.study.exercise.utils.ThreadUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tangjie on 8/23/16.
 * 仅用于后台定时向服务器请求数据,不负责业务数据的解析.
 */
public class PollService extends IntentService {

    public interface OnPollResultListener {
        void onPollResult(@Nullable String result);
    }

    private static Set<OnPollResultListener> sListener = new HashSet<>();

    /**
     * 增加轮询结果监听器,仅允许主线程调用.
     * @param listener
     */
    public static void addListener(@NonNull final OnPollResultListener listener) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("this method can only be called from main thread.");
        }
        sListener.add(listener);
    }

    /**
     * 移除轮询结果监听器,仅允许主线程调用.
     * @param listener
     */
    public static void removeListener(@NonNull final OnPollResultListener listener) {
        if (!ThreadUtils.isMainThread()) {
            throw new RuntimeException("this method can only be called from main thread.");
        }
        sListener.remove(listener);
    }

    public PollService() {
        super("PollService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO check data from server.
        notifyResult("");
    }

    private void notifyResult(final String result) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Set<OnPollResultListener> listeners = new HashSet<>();
                listeners.addAll(sListener);
                for (OnPollResultListener l : listeners) {
                    l.onPollResult(result);
                }
                listeners.clear();
            }
        });
    }
}
