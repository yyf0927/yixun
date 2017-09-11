package com.netease.study.exercise.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.netease.study.exercise.BuildConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by liuqijun on 11/22/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    static {
        System.loadLibrary("crash_handler");
    }

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private static CrashHandler mInstance;
    private Context mContext;

    public static CrashHandler getInstance() {
        if(mInstance == null) {
            synchronized (CrashHandler.class) {
                if(mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private CrashHandler() {
        this.mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        dumpStackTraceInfo(ex);

        if(mDefaultUncaughtExceptionHandler != null) {
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }

    }

    private void dumpStackTraceInfo(final Throwable ex) {

        if( !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "sdcard unmounted!");
            return;
        }

        File dir = mContext.getExternalFilesDir(null);
        if(dir == null) {
            Log.e(TAG, "directory is null");
            return;
        }

        String path = dir.getPath() + "/log";

        dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        long now = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US).format(new Date(now));

        File crash = new File(dir, "crash_" + time + ".trace");

        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(crash)));
            writer.println(time);
            dumpDeviceInfo(writer);
            writer.println();
            ex.printStackTrace(writer);
            writer.close();
        } catch (IOException e) {
            Log.e(TAG, "dump crash info failed!" + e.getMessage());
        }

    }


    @SuppressLint("NewApi")
    private void dumpDeviceInfo(PrintWriter writer) {

        //程序版本
        writer.print("App ver:");
        writer.println(BuildConfig.VERSION_NAME + "_" + BuildConfig.VERSION_CODE);

        //Android 系统版本
        writer.print("Android ver:");
        writer.println(Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);

        //Display ID
        writer.print("System:");
        writer.println(Build.DISPLAY);

        //手机制造商
        writer.print("Manufacturer:");
        writer.println(Build.MANUFACTURER);

        //手机型号
        writer.print("Model:");
        writer.println(Build.MODEL);

        //CPU架构
        writer.print("CPU ABI:");
        writer.println(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                Arrays.toString(Build.SUPPORTED_ABIS) : Build.CPU_ABI);

        //电量
        writer.print("Battery:");
        writer.println(battery());

        //Rooted
        writer.print("Rooted:");
        writer.println(isRooted());

        //Ram
        writer.print("Ram:");
        writer.println(ram());

        //Disk
        writer.print("Disk:");
        writer.println(disk());

        //网络信息
        writer.print("Network:");
        writer.println(networkInfo());
    }

    private String ram() {
        long total = getTotalMemory();
        long avail = getAvailMemory();
        if(total < 0) {
            return "--";
        } else {
            float ratio = (float) (avail * 100)/ total;
            return String.format(Locale.US, "%.01f%% [%s]", ratio, getSizeWithUnit(total));
        }
    }

    private long getAvailMemory() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        return info.availMem;
    }

    private long getTotalMemory() {
        long total = 0L;
        String str;
        if(!TextUtils.isEmpty(str = parseFile(new File("/proc/meminfo"), "MemTotal"))) {
            str = str.toUpperCase(Locale.US);
            if(str.endsWith("KB")) {
                total = getSize(str, "KB", 1024);
            } else if(str.endsWith("MB")) {
                total = getSize(str, "MB", 1048576);
            } else if(str.endsWith("GB")) {
                total = getSize(str, "GB", 1073741824);
            } else {
                total = -1;
            }
        }

        return total;
    }

    private long getSize(String size, String uint, int factor) {
        return Long.parseLong(size.split(uint)[0].trim()) * factor;
    }

    private String parseFile(File file, String filter) {
        String str = null;
        if(file.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file), 1024);
                String line;
                while ((line = reader.readLine()) != null) {
                    Pattern pattern = Pattern.compile("\\s*:\\s*");
                    String[] ret = pattern.split(line, 2);
                    if(ret != null && ret.length > 1 && ret[0].equals(filter)) {
                        str = ret[1];
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return str;
    }

    private String disk() {
        long[] info = getSdcardInfo();
        long total = info[0];
        long avail = info[1];
        if( total <= 0) {
            return "--";
        } else {
            float ratio = (float) (avail * 100)/ total;
            return String.format(Locale.US, "%.01f%% [%s]", ratio, getSizeWithUnit(total));
        }
    }

    private String getSizeWithUnit(long size) {
        if(size >= 1073741824) {
            float i = (float) (size / 1073741824);
            return String.format(Locale.US, "%.02f GB", i);
        } else if(size > 1048576) {
            float i = (float) (size / 1048576);
            return String.format(Locale.US, "%.02f MB", i);
        } else {
            float i = (float) (size / 1024);
            return String.format(Locale.US, "%.02f KB", i);
        }
    }

    private long[] getSdcardInfo() {
        long[] info = new long[2];
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            if(Build.VERSION.SDK_INT >= 18) {
                long bSize = sf.getBlockSizeLong();
                long bCount = sf.getBlockCountLong();
                long available = sf.getAvailableBlocksLong();
                info[0] = bSize * bCount;
                info[1] = bSize * available;
            } else {
                long bSize = sf.getBlockSize();
                long bCount = sf.getBlockCount();
                long available = sf.getAvailableBlocks();
                info[0] = bSize * bCount;
                info[1] = bSize * available;
            }
        }
        return info;
    }

    private String networkInfo() {
        String info = "unknown";

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null) {
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    info = networkInfo.getTypeName();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(networkInfo.getTypeName());
                    sb.append(" [");
                    String networkOperatorName = getNetworkOperatorName();
                    if(!TextUtils.isEmpty(networkOperatorName)) {
                        sb.append(networkOperatorName);
                        sb.append("#");
                    }
                    sb.append(networkInfo.getSubtypeName());
                    sb.append("]");
                    info = sb.toString();
                }
            }
        }

        return info;
    }

    private String getNetworkOperatorName() {
        if(checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getNetworkOperatorName();
        } else {
            return "unknown";
        }
    }

    private boolean checkPermission(String permission) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        return mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private String battery() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = mContext.registerReceiver(null, filter);

        if(intent != null) {
            int level = intent.getIntExtra("level", -1);
            int scale = intent.getIntExtra("scale", -1);
            if(scale != -1) {
                return String.format(Locale.US, "%d %%", (level * 100) / scale);
            }
        }

        return "--";
    }

    private boolean isRooted() {
        boolean isEmulator = isEmulator();
        String tags = Build.TAGS;

        if(!isEmulator && tags != null && tags.contains("test-keys")) {
            return true;
        }

        if(new File("system/app/Superuser.apk").exists()) {
            return true;
        }

        if(!isEmulator && new File("/system/xbin/su").exists()) {
            return true;
        }

        return false;
    }

    private boolean isEmulator() {
        if(!TextUtils.isEmpty(Build.MODEL) && Build.MODEL.toLowerCase().contains("sdk")) {
            return true;
        }

        if(!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("unknown")) {
            return true;
        }

        if(!TextUtils.isEmpty(Build.DEVICE) && Build.DEVICE.toLowerCase().contains("genetic")) {
            return true;
        }

        return false;
    }

    public static int crash() {
        return 100/0;
    }

    public native static void nativeCrash();




}
