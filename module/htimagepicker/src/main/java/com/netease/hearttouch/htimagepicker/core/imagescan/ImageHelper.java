/*
 * Copyright by Netease (c) 2016.
 * This source code is licensed under the MIT-style license found in the LICENSE file
 *  in the root directory of this source tree.
 */

package com.netease.hearttouch.htimagepicker.core.imagescan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Filter;

/**
 * Created by zyl06 on 3/28/16.
 */
public class ImageHelper {
    private static HashMap<Integer, Image> sId2Image = new HashMap<>();
    public static Image findImage(ImageFolder imageFolder, int imageId) {
        Image result = sId2Image.get(imageId);

        if (result == null) {
            if (imageFolder != null && imageFolder.getImages() != null) {
                List<Image> images = imageFolder.getImages();
                int size = images.size();
                for (int i=0; i<size; ++i) {
                    Image image = images.get(i);
                    if (image.getId() == imageId) {
                        sId2Image.put(imageId, image);
                        result = image;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void setSelectFlag(List<Image> images, boolean isSelected) {
        for (Image img : images) {
            img.setSelected(isSelected);
        }
    }

    public static void updateSelection(List<Image> from, List<Image> to) {
        if (from == null) {
            from = new ArrayList<Image>();
        }
        if (to == null) {
            to = new ArrayList<Image>();
        }

        from.removeAll(to);
        to.addAll(from);

        List<Image> tmp = new ArrayList<>();
        for (Image img : to) {
            if (img.isSelected()) {
                tmp.add(img);
            }
        }

        to.clear();
        to.addAll(tmp);
    }
}
