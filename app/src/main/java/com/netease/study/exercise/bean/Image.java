package com.netease.study.exercise.bean;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Gil on 04/03/2014.
 */
public class Image implements Serializable {
    private static final long serialVersionUID = -1250174338645277705L;

    public Uri mUri;
    public int mOrientation;

    public Image(Uri uri, int orientation) {
        mUri = uri;
        mOrientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return !(mUri != null ? !mUri.equals(image.mUri) : image.mUri != null);

    }

    @Override
    public int hashCode() {
        return mUri != null ? mUri.hashCode() : 0;
    }
}
