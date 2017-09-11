package com.netease.study.exercise.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public final static SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @param date
     * @param fmt  DateUtils.myFmt1  DateUtils.myFmt2
     * @return
     */
    public static String date2Str(Date date, SimpleDateFormat fmt) {
        return fmt.format(date);
    }

    public static String date2str(Date date) {
        return myFmt2.format(date);
        //return SimpleDateFormat.getDateInstance().format(date);
    }

    public static Date str2Date(String str) {
        try {
            return myFmt1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
        //return SimpleDateFormat.getDateInstance().format(date);
    }

    public static String dateStr2str(String str) {
        try {
            return date2str(myFmt1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String dateStr2strShort(String str) {
        try {
            return myFmt1.format(myFmt1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static long dateStr2TimeStamp(String str) {
        try {
            Date date = myFmt1.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }
}
