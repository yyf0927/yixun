/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.uiconfig;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netease.hearttouch.htimagepicker.core.camera.HTBaseCameraFragment;
import com.netease.hearttouch.htimagepicker.core.constants.C;
import com.netease.hearttouch.htimagepicker.core.imagecut.HTBaseImageCutActivity;
import com.netease.hearttouch.htimagepicker.core.imagepick.activity.HTBaseImagePickActivity;
import com.netease.hearttouch.htimagepicker.core.imagepreview.activity.HTBaseImagePreviewActivity;
import com.netease.hearttouch.htimagepicker.core.util.storage.StorageUtil;
import com.netease.hearttouch.htimagepicker.defaultui.camera.HTCameraFragment;
import com.netease.hearttouch.htimagepicker.defaultui.imagecut.activity.HTImageCutActivity;
import com.netease.hearttouch.htimagepicker.defaultui.imagepick.activity.HTImagePickActivity;
import com.netease.hearttouch.htimagepicker.defaultui.imagepreview.activity.HTMultiImagesPreviewActivity;
import com.netease.hearttouch.htimagepicker.defaultui.imagepreview.activity.HTSingleImagePreviewActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zyl06 on 3/21/16.
 */
public class BaseUIConfig {
    private static final String TAG = "HT_BaseUIConfig";

    Class mImagePickActivityClazz = HTImagePickActivity.class;
    Class mSingleImagePreviewActivityClazz = HTSingleImagePreviewActivity.class;
    Class mMultiImagePreviewActivityClazz = HTMultiImagesPreviewActivity.class;
    Class mImageCutActivityClazz = HTImageCutActivity.class;
    Class mCameraFragmentClazz = HTCameraFragment.class;
    boolean mIsUseSystemCamera = true; // 如果为true，则 mCameraFragmentClazz 参数的设置无效

    public BaseUIConfig() {
    }

    /**
     * @return 拍照的照片保存的文件名，包含路径
     */
    public String getPhotoFileSavePath() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + "_" + C.FileSuffix.JPG;

        return StorageUtil.getWriteSystemCameraPath(fileName, true);
    }

    public Class getImagePickActivityClass() {
        return mImagePickActivityClazz;
    }

    public Class getSingleImagePreviewActivity() {
        return mSingleImagePreviewActivityClazz;
    }

    public Class getMultiImagePreviewActivityClazz() {
        return mMultiImagePreviewActivityClazz;
    }

    public Class getImageCutActivityClazz() {
        return mImageCutActivityClazz;
    }

    public Class getCameraFragmentClazz() {
        return mCameraFragmentClazz;
    }

    public boolean isUseSystemCamera() {
        return mIsUseSystemCamera || mCameraFragmentClazz == null;
    }

    public static class UIConfigBuilder<T extends BaseUIConfig> {
        protected T mUIConfig;

        protected UIConfigBuilder(T UIConfig) {
            mUIConfig = UIConfig;
        }

        public UIConfigBuilder<T> setImagePickerActivityClass(@Nullable Class imagePickActivityClazz) {
            if (checkClassExtendsBase(imagePickActivityClazz, HTBaseImagePickActivity.class)) {
                mUIConfig.mImagePickActivityClazz = imagePickActivityClazz;
            } else if (imagePickActivityClazz != null) {
                Log.e(TAG, "pickImageActivity class is not extends of HTBaseImagePickActivity");
            }

            return this;
        }

        public UIConfigBuilder<T> setSingleImagePreviewActivityClass(@Nullable Class singleImagePreviewActivityClazz) {
            if (checkClassExtendsBase(singleImagePreviewActivityClazz, HTBaseImagePreviewActivity.class)) {
                mUIConfig.mSingleImagePreviewActivityClazz = singleImagePreviewActivityClazz;
            } else if (singleImagePreviewActivityClazz != null) {
                Log.e(TAG, "singleImagePreviewActivity class is not extends of HTBaseImagePreviewActivity");
            }

            return this;
        }

        public UIConfigBuilder<T> setMultiImagePreviewActivityClazz(@Nullable Class multiImagePreviewActivityClazz) {
            if (checkClassExtendsBase(multiImagePreviewActivityClazz, HTBaseImagePreviewActivity.class)) {
                mUIConfig.mMultiImagePreviewActivityClazz = multiImagePreviewActivityClazz;
            } else if (multiImagePreviewActivityClazz != null) {
                Log.e(TAG, "multiImagePreviewActivity class is not extends of HTBaseImagePreviewActivity");
            }

            return this;
        }

        public UIConfigBuilder<T> setImageCutActivityClazz(@Nullable Class imageCutActivityClazz) {
            if (checkClassExtendsBase(imageCutActivityClazz, HTBaseImageCutActivity.class)) {
                mUIConfig.mImageCutActivityClazz = imageCutActivityClazz;
            } else if (imageCutActivityClazz != null) {
                Log.e(TAG, "imageCutActivityClazz class is not extends of HTBaseImageCutActivity");
            }

            return this;
        }

        public UIConfigBuilder<T> setCameraFragmentClazz(@Nullable Class cameraFragmentClazz) {
            if (checkClassExtendsBase(cameraFragmentClazz, HTBaseCameraFragment.class)) {
                mUIConfig.mCameraFragmentClazz = cameraFragmentClazz;
            } else if (cameraFragmentClazz != null) {
                Log.e(TAG, "cameraFragmentClazz class is not extends of HTBaseCameraFragment");
            }

            return this;
        }

        public UIConfigBuilder<T> setUseSystemCamera(boolean isUseSystemCamera) {
            mUIConfig.mIsUseSystemCamera = isUseSystemCamera;
            return this;
        }

        public T build() {
            return mUIConfig;
        }

        private boolean checkClassExtendsBase(Class clz, @NonNull Class base) {
            Class superClz = clz;
            while (superClz != null && superClz != base) {
                superClz = superClz.getSuperclass();
            }

            return superClz == base;
        }
    }
}
