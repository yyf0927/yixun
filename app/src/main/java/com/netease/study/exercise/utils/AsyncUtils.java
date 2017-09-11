package com.netease.study.exercise.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zyl06 on 9/4/16.
 */

public class AsyncUtils {
    private static ExecutorService sSingleExecutor = null;
    public static ExecutorService getSingleExecutor() {
        if (sSingleExecutor == null) {
            synchronized (AsyncUtils.class) {
                if (sSingleExecutor == null) {
                    sSingleExecutor = Executors.newSingleThreadExecutor();
                }
            }
        }

        return sSingleExecutor;
    }

    private static ExecutorService sMultiExecutor = null;
    public static ExecutorService getMultiExecutor() {
        if (sMultiExecutor == null) {
            synchronized (AsyncUtils.class) {
                if (sMultiExecutor == null) {
                    int availableProcessor = Runtime.getRuntime().availableProcessors();
                    sMultiExecutor = new ThreadPoolExecutor(availableProcessor + 1,
                            2 * availableProcessor,
                            30L, TimeUnit.SECONDS,
                            new LinkedBlockingDeque<Runnable>(20),
                            new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return sMultiExecutor;
    }


    public static void runOnSingleExecutor(Runnable runnable) {
        if (runnable != null) {
            getSingleExecutor().execute(runnable);
        }
    }

    public static void runOnMultiExecutor(Runnable runnable) {
        if (runnable != null) {
            getMultiExecutor().execute(runnable);
        }
    }

    public static <T> Future<T> submitOnSingleExecutor(Callable<T> callable) {
        if (callable != null) {
            return getSingleExecutor().submit(callable);
        }
        return null;
    }

    public static <T> Future<T> submitOnMultiExecutor(Callable<T> callable) {
        if (callable != null) {
            return getMultiExecutor().submit(callable);
        }
        return null;
    }

    public static synchronized void destroy() {
        if (sSingleExecutor != null && !sSingleExecutor.isShutdown()) {
            sSingleExecutor.shutdown();
            sSingleExecutor = null;
        }
        if (sMultiExecutor != null && !sMultiExecutor.isShutdown()) {
            sMultiExecutor.shutdown();
            sMultiExecutor = null;
        }
    }
}
