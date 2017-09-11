package com.netease.study.exercise.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by zyl06 on 9/4/16.
 */

public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        if (toTransform == null) {
            return null;
        }

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int size = Math.min(width, height);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        return result;
    }

    @Override
    public String getId() {
        return "GlideCircleTransform";
    }
}
