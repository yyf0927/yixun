package com.netease.study.exercise.bean;

import android.net.Uri;

import java.io.Serializable;

public class ImageBean implements Serializable {
    private static final long serialVersionUID = 2825764720525662922L;

    private Uri mUri;

    public ImageBean(Uri uri) {
        mUri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageBean image = (ImageBean) o;

        return !(mUri != null ? !mUri.equals(image.mUri) : image.mUri != null);

    }

    public Uri getUri() {
        return mUri;
    }

    @Override
    public int hashCode() {
        return mUri != null ? mUri.hashCode() : 0;
    }
}
