package com.netease.study.exercise.activity.imagepick;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.netease.hearttouch.htimagepicker.core.imagescan.Image;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageFolder;
import com.netease.hearttouch.htimagepicker.core.imagescan.ImageInfoUtil;
import com.netease.hearttouch.htimagepicker.core.util.ContextUtil;
import com.netease.hearttouch.htimagepicker.core.util.ViewHolder;
import com.netease.hearttouch.htimagepicker.core.util.image.ImageUtil;
import com.netease.study.exercise.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 9/16/16.
 */

public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Image> mImages = new ArrayList<>();
    private LayoutInflater mInflater;
    private static final int IMAGE_WIDTH = (int) ContextUtil.INSTANCE.getScreenWidthDp() / 3;
    private static final int IMAGE_HEIGHT = IMAGE_WIDTH;
    private static SelectImageChangeListener mSelectImageChangeListener;

    public ImageGridAdapter(Context context, ImageFolder imageFolder, SelectImageChangeListener listener) {
        mContext = context;
        mSelectImageChangeListener = listener;
        mInflater = LayoutInflater.from(mContext);

        initAllImages(imageFolder);
    }

    public void changeImageFolder(ImageFolder imageFolder) {
        initAllImages(imageFolder);
        notifyDataSetChanged();
    }

    private void initAllImages(ImageFolder imageFolder) {
        if (imageFolder != null && imageFolder.getImages() != null) {
            mImages = imageFolder.getImages();
        }
    }

    @Override
    public int getCount() {
        // 0 是拍照
        return mImages.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        // 0 是拍照
        return mImages.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // 拍照item，图片选择item
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (position == 0)
                convertView = mInflater.inflate(R.layout.item_imagepicker_gridview_camera, null);
            else {
                convertView = mInflater.inflate(R.layout.item_imagepicker_gridview_pic, null);
            }
            int hMargin = (int) ContextUtil.INSTANCE.getDimen(com.netease.hearttouch.htimagepicker.R.dimen.ne_pick_image_horizontal_margin);
            int hSpace = (int) ContextUtil.INSTANCE.getDimen(com.netease.hearttouch.htimagepicker.R.dimen.ne_pick_image_grid_internal_space);
            int height = (ContextUtil.INSTANCE.getScreenWidthPixel() - 2 * hMargin - 2 * hSpace) / 3;
            GridView.LayoutParams lp = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, height);
            convertView.setLayoutParams(lp);
        }

        if (position > 0) {
            final Image image = mImages.get(position - 1);

            ImageView photoThumbnail = ViewHolder.get(convertView, R.id.pick_image_photo);
            String thumbUrl = ImageInfoUtil.getThumbnailPathWithId(image.getId(), image.getUriPath());

            String oldThumbUrl = (String) photoThumbnail.getTag();
            if (oldThumbUrl == null || !oldThumbUrl.equals(thumbUrl)) {
                photoThumbnail.setTag(thumbUrl);
                Uri uri = Uri.parse(thumbUrl);
                ImageUtil.setImagePath(photoThumbnail, uri.getPath(), IMAGE_WIDTH, IMAGE_HEIGHT, true);
            }

            CheckBox pickMark = ViewHolder.get(convertView, R.id.cb_pick_image_mark);
            pickMark.setChecked(image.isSelected());

            pickMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image.setSelected(((CheckBox) v).isChecked());
                    if (mSelectImageChangeListener != null) {
                        boolean success = mSelectImageChangeListener.onImageSelectionChange(image);
                        if (!success) { // 如果没有成功，需要将 checkbox 重置回来
                            ((CheckBox) v).setChecked(image.isSelected());
                        }
                    }
                }
            });
        }

        return convertView;
    }
}