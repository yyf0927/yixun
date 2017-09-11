package com.netease.study.exercise.utils;

import android.graphics.Color;

/**
 * Created by netease on 16/11/8.
 */

public class ColorUtils {
    public static int getColor700from500(int color) {
        float[] result = new float[3];
        Color.colorToHSV(color, result);
        result[2]  = result[2] * (color == Color.WHITE ? 0.8f : 0.85f);
        return Color.HSVToColor(result);
    }
}
