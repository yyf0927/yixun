package com.netease.study.exercise.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.LostDetailActivity;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.share.ShareManager;
import com.netease.study.exercise.utils.image.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;

public class LostInfoViewHolder extends LostInfoBaseViewHolder implements View.OnClickListener {
    private final String TEP_SEX_AGE = AppProfile.getContext().getResources().getString(R.string.ageNum);
    private final String TEP_COMMENT = AppProfile.getContext().getResources().getString(R.string.commentNum);

    private TextView mLostListTime;
    private TextView mLostListName;
    private ImageView mLostListPic1;
    private ImageView mLostListPic2;
    private ImageView mLostListPic3;
    private List<ImageView> mImageViews;
    private TextView mLostListSexAge;
    private TextView mLostListLocation;
    private TextView mLostListDetail;
    private TextView mLostListDistance;
    private TextView mLostListComment;
    private View mComment;
    private View mShare;

    private LostListItemBean mLostListItemBean;

    public LostInfoViewHolder(View v) {
        super(v);

        mLostListName = (TextView) v.findViewById(R.id.lost_list_name);
        mLostListPic1 = (ImageView) v.findViewById(R.id.lost_list_pic1);
        mLostListPic2 = (ImageView) v.findViewById(R.id.lost_list_pic2);
        mLostListPic3 = (ImageView) v.findViewById(R.id.lost_list_pic3);
        mLostListTime = (TextView) v.findViewById(R.id.lost_list_time);
        mLostListSexAge = (TextView) v.findViewById(R.id.lost_list_sex_age);
        mLostListLocation = (TextView) v.findViewById(R.id.lost_list_location);
        mLostListDetail = (TextView) v.findViewById(R.id.lost_list_detail);
        mLostListDistance = (TextView) v.findViewById(R.id.lost_list_distance);
        mLostListComment = (TextView) v.findViewById(R.id.lost_list_comment);

        mComment = v.findViewById(R.id.lost_btn_comment);
        mShare = v.findViewById(R.id.lost_btn_share);
        mComment.setOnClickListener(this);
        mShare.setOnClickListener(this);
        v.setOnClickListener(this);

        mImageViews = new ArrayList<>();
        mImageViews.add(mLostListPic1);
        mImageViews.add(mLostListPic2);
        mImageViews.add(mLostListPic3);
    }

    public void fillData(LostListItemBean p) {
        mLostListItemBean = p;
        if (p.getType() == 0) {
            mLostListTime.setVisibility(View.VISIBLE);
        } else {
            mLostListTime.setVisibility(View.GONE);
        }

        mLostListName.setText(p.getLostName());
        mLostListSexAge.setText(String.format(TEP_SEX_AGE, p.getLostSex() + " " + p.getYear()));
        mLostListLocation.setText(p.getLostPlace());
        mLostListDetail.setText(p.getDescribes());

        mLostListDistance.setVisibility(View.GONE);
//        if (!TextUtils.isEmpty(p.getDistanceStr())) {
//            mLostListDistance.setVisibility(View.VISIBLE);
//            mLostListDistance.setText(p.getDistanceStr());
//        } else {
//            mLostListDistance.setVisibility(View.GONE);
//        }

        mLostListComment.setText(String.format(TEP_COMMENT, p.getCommentNum()));

        if (p.isShowTime()) {
            mLostListTime.setVisibility(View.VISIBLE);
            mLostListTime.setText(p.getTimeStr());
            if (p.getFlag()) {
                mLostListTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_flash, 0, 0, 0);
            } else {
                mLostListTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_time, 0, 0, 0);
            }
        } else {
            mLostListTime.setVisibility(View.GONE);
        }

        if (p.getPhotoName() != null) {
            ImageView tmp = null;
            String[] photos = p.getPhotos();
            for (int i = 0; i < mImageViews.size(); i++) {
                tmp = mImageViews.get(i);
                if (photos != null && i < photos.length) {
                    ImageUtils.setUrl(tmp, photos[i], dp2px(150), dp2px(150));
                    tmp.setVisibility(View.VISIBLE);
                } else {
                    tmp.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(final View view) {
        if (view == mShare) {
            ShareManager.share((Activity) view.getContext(), mLostListItemBean);
        } else {
            LostDetailActivity.launch(view.getContext(), mLostListItemBean.getId());
        }
    }
}
