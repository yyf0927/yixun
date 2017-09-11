/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core;

import com.netease.hearttouch.htimagepicker.core.constants.C;
import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageFolder;

import java.util.List;

/**
 * Created by zyl06 on 3/21/16.
 */
public class HTPickParamsConfig {
    private HTImageFrom from = HTImageFrom.FROM_LOCAL;
    private String mImageFolderName;
    private List<Image> mImages;
    private int selectLimit = 1;
    private boolean isCut = false;
    private float cutRatio = 1.0f;
    private String title = C.EMPTY;

    public HTPickParamsConfig(HTImageFrom from, String imageFolderName, List<Image> images) {
        this.from = from;
        this.mImageFolderName = imageFolderName;
        this.mImages = images;
    }

    public HTImageFrom getFrom() {
        return from;
    }

    public String getImageFolderName() {
        return mImageFolderName;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public int getSelectLimit() {
        return selectLimit;
    }

    public boolean isCut() {
        return isCut;
    }

    public float getCutRatio() {
        return cutRatio;
    }

    public String getTitle() {
        return title;
    }

    public static HTPickParamsConfigBuilder newBuilder() {
        return new HTPickParamsConfigBuilder();
    }

    public static class HTPickParamsConfigBuilder {
        private HTPickParamsConfig mParamsConfig;

        public HTPickParamsConfigBuilder() {
            mParamsConfig = new HTPickParamsConfig(HTImageFrom.FROM_LOCAL, null, null);
        }

        public HTPickParamsConfigBuilder setFrom(HTImageFrom from) {
            mParamsConfig.from = from;
            return this;
        }

        public HTPickParamsConfigBuilder setImageFolderName(String imageFolderName) {
            mParamsConfig.mImageFolderName = imageFolderName;
            return this;
        }

        public HTPickParamsConfigBuilder setImages(List<Image> images) {
            mParamsConfig.mImages = images;
            return this;
        }

        public HTPickParamsConfigBuilder setSelectLimit(int selectLimit) {
            mParamsConfig.selectLimit = selectLimit;
            return this;
        }

        public HTPickParamsConfigBuilder setCut(boolean bCut) {
            mParamsConfig.isCut = bCut;
            return this;
        }

        public HTPickParamsConfigBuilder setCutRatio(float cutRatio) {
            mParamsConfig.cutRatio = cutRatio;
            return this;
        }

        public HTPickParamsConfigBuilder setTitle(String title) {
            mParamsConfig.title = title;
            return this;
        }

        public HTPickParamsConfig build() {
            return mParamsConfig;
        }
    }
}
