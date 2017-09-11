package com.netease.study.exercise.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by zyl06 on 8/26/16.
 */

public class DiskUtils {

    public static long getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return blockSize * availableBlocks;
    }
}
