package com.netease.study.exercise.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by netease on 16/11/8.
 */

public class DrawableUtils {
    public static Drawable getColorDrawable(Drawable drawable, int color) {
        if (drawable == null) {
            return null;
        }
        Drawable drawableCompat = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(drawableCompat, ColorStateList.valueOf(color));
        return drawableCompat;
    }
}
