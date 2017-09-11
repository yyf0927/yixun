package com.netease.study.exercise.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 本地验证工具类
 */
public class ValidateUtils {
    private static final String sPATTREN_PWD = "[0-9A-Za-z]{6,20}$";
    private static final String sPATTREN_MOBILE_NUM = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static final String sPATTREN_HEIGHT = "^(-?\\d+)(\\.\\d+)?$";
    private static final String sPATTREN_AGE = "[0-9]+";
    //匹配 2004-04-30 | 2004-02-29
    private static final String sPATTREN_DATE = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

    public static boolean validateMobileNum(CharSequence mobileNum) {
        return match(mobileNum, sPATTREN_MOBILE_NUM);
    }

    public static boolean validatePwd(CharSequence pwd) {
        return match(pwd, sPATTREN_PWD);
    }

    public static boolean validateHeight(CharSequence pwd) {
        return match(pwd, sPATTREN_HEIGHT);
    }

    public static boolean match(CharSequence data, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(data);
        return m.matches();
    }

    public static boolean validateAge(String age) {
        boolean result = false;
        if (age.isEmpty()) {
            result = true;
        } else if (age.matches(sPATTREN_AGE)) {
            int ageInt = Integer.parseInt(age);
            if (0 < ageInt && ageInt < 200) {
                result = true;
            }
        }

        return result;
    }

    //匹配 2004-04-30 | 2004-02-29
    public static boolean validateDate(String date) {
        boolean result = false;
        if (date.isEmpty()) {
            result = true;
        } else if (date.matches(sPATTREN_DATE)) {
            result = true;
        }
        return result;
    }
}
