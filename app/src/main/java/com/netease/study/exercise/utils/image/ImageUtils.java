package com.netease.study.exercise.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Created by zyl06 on 9/4/16.
 */
public class ImageUtils {

    private static MemoryCache sMemoryCache = null;
    private static GlideCircleTransform sCircleTransform = null;
    private static SparseArray<GlideRoundTransform> sRoundTransforms = new SparseArray<>();

    /*package*/
    static MemoryCache getMemoryCache(Context context) {
        if (sMemoryCache == null) {
            synchronized (ImageUtils.class) {
                if (sMemoryCache == null) {
                    MemorySizeCalculator calculator = new MemorySizeCalculator(context);
                    sMemoryCache = new LruResourceCache(calculator.getMemoryCacheSize());
                }
            }
        }

        return sMemoryCache;
    }

    private static synchronized BitmapTransformation getRoundBitmapTransform(Context context, int radiusDp) {
        GlideRoundTransform transformation = sRoundTransforms.get(radiusDp);
        if (transformation == null) {
            transformation = new GlideRoundTransform(context, radiusDp);
            sRoundTransforms.put(radiusDp, transformation);
        }

        return transformation;
    }

    private static BitmapTransformation getCircleBitmapTransform(Context context) {
        if (sCircleTransform == null) {
            synchronized (ImageUtils.class) {
                if (sCircleTransform == null) {
                    sCircleTransform = new GlideCircleTransform(context);
                }
            }
        }

        return sCircleTransform;
    }

    // 针对网络图片的接口
    /**
     * 给 imageView 设置网络图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     */
    public static void setUrl(ImageView imageView, String url) {
        setUrl(imageView, url, 0, 0, null);
    }

    /**
     * 给 imageView 设置网络图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     * @param width     网络图片在 url 中添加的宽度大小信息
     * @param height    网络图片在 url 中添加的高度大小信息
     */
    public static void setUrl(ImageView imageView, String url, int width, int height) {
        setUrl(imageView, url, width, height, null);
    }

    /**
     * 给 imageView 设置网络图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     * @param radiusDp  图片显示的圆角半径
     */
    public static void setUrl(ImageView imageView, String url, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setUrl(imageView, url, 0, 0, transformation);
    }

    /**
     * 给 imageView 设置网络图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     * @param width     网络图片在 url 中添加的宽度大小信息
     * @param height    网络图片在 url 中添加的高度大小信息
     * @param radiusDp  图片显示的圆角半径
     */
    public static void setUrl(ImageView imageView, String url, int width, int height, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setUrl(imageView, url, width, height, transformation);
    }

    /**
     * 给 imageView 设置网络图片，并显示圆形图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     */
    public static void setCircleUrl(ImageView imageView, String url) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setUrl(imageView, url, 0, 0, transformation);
    }

    /**
     * 给 imageView 设置网络图片，并显示圆形图片
     * @param imageView 图片控件
     * @param url       网络图片的 url
     * @param width     网络图片在 url 中添加的宽度大小信息
     * @param height    网络图片在 url 中添加的高度大小信息
     */
    public static void setCircleUrl(ImageView imageView, String url, int width, int height) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setUrl(imageView, url, width, height, transformation);
    }

    private static void setUrl(ImageView imageView, String url, int width, int height, BitmapTransformation transformation) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImgUrl(url, width, height) :
                url;

        DrawableRequestBuilder<String> builder = Glide.with(imageView.getContext())
                .load(sizeUrl)
                .centerCrop()
                .animate(android.R.anim.fade_in);
        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }
        builder.into(imageView);
    }

    // 针对资源图片的接口
    /**
     * 给 imageView 设置本地资源图片
     * @param imageView 图片控件
     * @param resId     本地图片资源 id
     */
    public static void setResId(ImageView imageView, int resId) {
        setResId(imageView, resId, null);
    }

    /**
     * 给 imageView 设置本地资源图片
     * @param imageView 图片控件
     * @param resId     本地图片资源 id
     * @param radiusDp  图片显示的圆角半径
     */
    public static void setResId(ImageView imageView, int resId, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setResId(imageView, resId, transformation);
    }

    /**
     * 给 imageView 设置本地资源图片，并显示圆形图片
     * @param imageView 图片控件
     * @param resId     本地图片资源 id
     */
    public static void setCircleResId(ImageView imageView, int resId) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setResId(imageView, resId, transformation);
    }

    private static void setResId(ImageView imageView, int resId, BitmapTransformation transformation) {
        DrawableRequestBuilder<Integer> builder = Glide.with(imageView.getContext())
                .load(resId)
                .centerCrop()
                .animate(android.R.anim.fade_in);
        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }
        builder.into(imageView);
    }

    // 针对 sdcard 图片的接口
    /**
     * 给 imageView 设置本地存储卡中的图片
     * @param imageView 图片控件
     * @param filePath  图片存储路径
     */
    public static void setFilePath(ImageView imageView, String filePath) {
        setFilePath(imageView, filePath, null);
    }

    /**
     * 给 imageView 设置本地存储卡中的图片
     * @param imageView 图片控件
     * @param filePath  图片存储路径
     * @param radiusDp  图片显示的圆角半径
     */
    public static void setFilePath(ImageView imageView, String filePath, int radiusDp) {
        BitmapTransformation transformation = getRoundBitmapTransform(imageView.getContext(), radiusDp);
        setFilePath(imageView, filePath, transformation);
    }

    /**
     * 给 imageView 设置本地存储卡中的图片，并显示圆形图片
     * @param imageView 图片控件
     * @param filePath  图片存储路径
     */
    public static void setCircleFilePath(ImageView imageView, String filePath) {
        BitmapTransformation transformation = getCircleBitmapTransform(imageView.getContext());
        setFilePath(imageView, filePath, transformation);
    }

    private static void setFilePath(ImageView imageView, String filePath, BitmapTransformation transformation) {
        DrawableRequestBuilder<String> builder = Glide.with(imageView.getContext())
                .load(filePath)
                .centerCrop()
                .animate(android.R.anim.fade_in);
        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }
        builder.into(imageView);
    }

    // 其他
    /**
     * 获取磁盘图片缓存的大小，
     * @param context 上下文环境
     * @return 磁盘缓存大小，单位 byte
     */
    public static long getDiskCacheSize(Context context) {
        File diskCacheDir = getDiskCacheDir(context);
        return getFolderSize(diskCacheDir);
    }

    /**
     * 清除磁盘缓存
     * @param context   上下文环境
     * @param listener  磁盘缓存清理完成的回调监听
     */
    public static void clearDiskCache(final Context context, final ClearDiskCacheListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                if (listener != null) {
                    listener.onDiskCacheCleared();
                }
            }
        });
        thread.start();
    }

    /**
     * 清除内存缓存
     * @param context   上下文环境
     */
    public static void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 预获取网络图片至本地缓存
     * @param context   上下文环境
     * @param url       网络图片 url
     * @param width     网络图片在 url 中添加的宽度大小信息
     * @param height    网络图片在 url 中添加的高度大小信息
     */
    public static void prefetch(Context context, String url, int width, int height) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImgUrl(url, width, height) :
                url;
        Glide.with(context).load(sizeUrl).downloadOnly(width, height);
    }

    /**
     * 预获取网络图片至本地缓存
     * @param context   上下文环境
     * @param url       网络图片 url
     * @param width     网络图片在 url 中添加的宽度大小信息
     * @param height    网络图片在 url 中添加的高度大小信息
     * @param listener  网络图片下载完成的回调。当本地缓存本来就存在目标图片的，该监听也能被调用
     */
    public static void prefetch(Context context, String url, int width, int height, ImageLoadListener listener) {
        String sizeUrl = (width > 0 && height > 0) ?
                UrlGenerator.getImgUrl(url, width, height) :
                url;
        Glide.with(context).load(sizeUrl).downloadOnly(new DownloadTarget(listener, width, height));
    }

    private static File getDiskCacheDir(Context context) {
        File cacheDirectory = context.getCacheDir();
        return new File(cacheDirectory, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
    }

    private static long getFolderSize(File file) {
        long size = 0;
        try {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (File subFile : fileList) {
                    if (subFile.isDirectory()) {
                        size += getFolderSize(subFile);
                    } else {
                        size += subFile.length();
                    }
                }
            } else {
                size += file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    private static class DownloadTarget implements Target<File> {

        private Request mRequest = null;
        private ImageLoadListener mListener = null;
        private int mWidth = 0;
        private int mHeight = 0;

        public DownloadTarget(ImageLoadListener listener, int width, int height) {
            mListener = listener;
            mWidth = width;
            mHeight = height;
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            if (mListener != null) {
                mListener.onLoadStart();
            }
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            if (mListener != null) {
                mListener.onLoadFailed();
            }
        }

        @Override
        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
            if (mListener != null) {
                mListener.onLoadSuccess(resource);
            }
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {}

        @Override
        public void getSize(SizeReadyCallback cb) {
            cb.onSizeReady(mWidth, mHeight);
        }

        @Override
        public void setRequest(Request request) {
            mRequest = request;
        }

        @Override
        public Request getRequest() {
            return mRequest;
        }

        @Override
        public void onStart() {}

        @Override
        public void onStop() {}

        @Override
        public void onDestroy() {}
    }
}
