/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.imagepick.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.netease.hearttouch.htimagepicker.core.HTImageFrom;
import com.netease.hearttouch.htimagepicker.core.HTImagePicker;
import com.netease.hearttouch.htimagepicker.core.HTPickFinishedListener;
import com.netease.hearttouch.htimagepicker.core.HTPickParamsConfig;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.uiconfig.HTRuntimeUIConfig;
import com.netease.hearttouch.htimagepicker.core.uiconfig.HTUIConfig;
import com.netease.hearttouch.htimagepicker.core.imagepick.listener.ImagePickerListenerCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 3/21/16.
 */
public class ImagePickHelper {
    public static void start(Context context, HTPickParamsConfig paramConfig,
                             HTRuntimeUIConfig uiConfig, HTPickFinishedListener listener) {
        HTUIConfig htuiConfig = HTImagePicker.INSTANCE.getUIConfig();
        htuiConfig.setRuntimeUIConfig(uiConfig);
        start(htuiConfig.getImagePickActivityClass(),
                context,
                paramConfig.getFrom(),
                paramConfig.getImageFolderName(),
                paramConfig.getImages(),
                paramConfig.getSelectLimit(),
                paramConfig.isCut(),
                paramConfig.getCutRatio(),
                paramConfig.getTitle(),
                listener);
    }

    private static void start(@NonNull Class pickImageActivityClass,
                              Context context,
                              HTImageFrom from,
                              String imageFolderName,
                              List<Image> images,
                              int selectLimit,
                              boolean bCut,
                              float cutRatio,
                              String title,
                              HTPickFinishedListener listener) {

        Intent intent = new Intent(context, pickImageActivityClass);
        intent.putExtra(Extras.EXTRA_FROM, from);
        // 注释掉,在 HTBaseImagePickActivity 中判断输出文件路径是不是为空,如果为空则使用 HTImagePicker.INSTANCE.getUIConfig().getPhotoFileSavePath()
        // 避免在这里就要处理动态权限 READ_EXTERNAL_STORAGE
        // intent.putExtra(Extras.EXTRA_FILE_PATH, HTImagePicker.INSTANCE.getUIConfig().getPhotoFileSavePath());
        intent.putExtra(Extras.EXTRA_SELECT_LIMIT, selectLimit);
        intent.putExtra(Extras.EXTRA_NEED_CUT, bCut);
        intent.putExtra(Extras.EXTRA_CUT_RATIO, cutRatio);

        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(Extras.EXTRA_PICK_TITLE, title);
        }

        if (imageFolderName != null) {
            intent.putExtra(Extras.EXTRA_IMAGE_FOLDER_NAME, imageFolderName);
        }

        ArrayList<String> imagePaths = new ArrayList<>();
        if (images != null) {
            for (Image img : images) {
                imagePaths.add(img.getAbsolutePath());
            }
        }
        intent.putStringArrayListExtra(Extras.EXTRA_IMAGE_LIST, imagePaths);

        int key = ImagePickerListenerCache.insertImagePickerListener(context, listener);
        intent.putExtra(Extras.EXTRA_LISTENER_KEY, key);

        context.startActivity(intent);
    }
}
