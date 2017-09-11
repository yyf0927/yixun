package com.netease.study.exercise;

import android.content.Context;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.netease.study.exercise.utils.ThreadUtils;
import com.netease.study.exercise.utils.image.ClearDiskCacheListener;
import com.netease.study.exercise.utils.image.ImageLoadListener;
import com.netease.study.exercise.utils.image.ImageUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertNotNull;

/**
 * Created by zyl06 on 11/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class ImageUtilsTest {
    @Test
    public void testPrefetch0() {
        prefetch("http://img-sample.nos-eastchina1.126.net/Koala.jpg", new ImageLoadListener() {
            @Override
            public void onLoadSuccess(File resource) {
                assertNotNull(resource);
            }

            @Override
            public void onLoadFailed() {
                Assert.assertTrue(false);
            }

            @Override
            public void onLoadStart() {
                Log.i("TEST", "onLoadStart");
            }
        });
    }

    @Test
    public void testPrefetch1() {
        prefetch("http://img-sample.nos-eastchina1.126.net/not-exist.jpg", new ImageLoadListener() {
            @Override
            public void onLoadSuccess(File resource) {
                Assert.assertTrue(false);
            }

            @Override
            public void onLoadFailed() {
            }

            @Override
            public void onLoadStart() {
            }
        });
    }

    @Test
    public void testClearDiskCache() {
        final CountDownLatch latch = new CountDownLatch(1);

        final Context context = InstrumentationRegistry.getTargetContext();
        ImageUtils.clearDiskCache(context, new ClearDiskCacheListener() {
            @Override
            public void onDiskCacheCleared() {
                Assert.assertEquals(0, ImageUtils.getDiskCacheSize(context));
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDiskCache() {
        prefetch("http://img-sample.nos-eastchina1.126.net/Koala.jpg", new ImageLoadListener() {
            @Override
            public void onLoadSuccess(File resource) {
                Context context = InstrumentationRegistry.getTargetContext();
                long cacheSize = ImageUtils.getDiskCacheSize(context);
                Assert.assertTrue(cacheSize > resource.length());
            }
            @Override
            public void onLoadFailed() {}
            @Override
            public void onLoadStart() {}
        });
    }

    private void prefetch(final String url, final ImageLoadListener listener) {
        final CountDownLatch latch = new CountDownLatch(3);
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                ImageUtils.prefetch(InstrumentationRegistry.getTargetContext(), url, 200, 200, new ImageLoadListener() {
                    @Override
                    public void onLoadSuccess(File resource) {
                        latch.countDown();

                        if (listener != null) {
                            listener.onLoadSuccess(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed() {
                        latch.countDown();

                        if (listener != null) {
                            listener.onLoadFailed();
                        }
                    }

                    @Override
                    public void onLoadStart() {
                        latch.countDown();

                        if (listener != null) {
                            listener.onLoadStart();
                        }
                    }
                });
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
