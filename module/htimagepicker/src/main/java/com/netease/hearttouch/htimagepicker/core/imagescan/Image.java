/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.imagescan;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zyl06 on 3/28/16.
 */
public class Image implements Parcelable {
    private int mId;
    private String mName;
    private String mAbsolutePath;
    private String mUriPath;
    private long mLastModifiedTime;
    private boolean mIsSelected;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAbsolutePath() {
        return mAbsolutePath;
    }

    public String getUriPath() {
        return mUriPath;
    }

    public long getLastModifiedTime() {
        return mLastModifiedTime;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setAbsolutePath(String absolutePath) {
        mAbsolutePath = absolutePath;
    }

    public void setUriPath(String uriPath) {
        mUriPath = uriPath;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        mLastModifiedTime = lastModifiedTime;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    @Override
    public int describeContents() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mAbsolutePath);
        dest.writeString(mUriPath);
        dest.writeLong(mLastModifiedTime);
        dest.writeInt(mIsSelected ? 1 : 0);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            Image img = new Image();
            img.setId(source.readInt());
            img.setName(source.readString());
            img.setAbsolutePath(source.readString());
            img.setUriPath(source.readString());
            img.setLastModifiedTime(source.readLong());
            img.setSelected(source.readInt() == 1);

            return img;
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
