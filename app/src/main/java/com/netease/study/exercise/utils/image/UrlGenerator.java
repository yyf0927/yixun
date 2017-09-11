package com.netease.study.exercise.utils.image;

import java.util.Locale;

/**
 * Created by zyl06 on 9/4/16.
 */
public class UrlGenerator {
    public static final String sSizeFormat = "%s?imageView&thumbnail=%dx%d";

    public static String getImgUrl(String url, int width, int height) {
        return String.format(Locale.getDefault(), sSizeFormat, url, width, height);
    }
}
