package com.netease.study.exercise.utils;

import java.util.regex.Pattern;
import android.text.TextUtils;
/**
 * Created by zw on 16/10/29.
 */
public class PhoneNumberHelper {
    static final String PHONE_NUMBER = "^(\\+?0*86-?)?(0+)?(1[23456789]\\d{9})$";
    static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER);

    public static final boolean isMobile(String str) {
        return !TextUtils.isEmpty(str) && PhoneNumberHelper.PHONE_NUMBER_PATTERN.matcher(str).matches();
    }


}
