package com.netease.study.exercise.utils.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.netease.study.exercise.ExerciseConst;
import com.netease.study.exercise.utils.DiskUtils;

/**
 * Created by zyl06 on 9/4/16.
 */

public class GHGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        long availableSize = DiskUtils.getSDAvailableSize();
        long diskCacheSize = availableSize > 500 * ExerciseConst.M ?
                250 * ExerciseConst.M : availableSize / 2;

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, (int) diskCacheSize))
                .setMemoryCache(ImageUtils.getMemoryCache(context))
                .setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // 配置数据类型的解析器。我们这里并不需要
    }
}
