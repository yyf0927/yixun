package com.netease.study.exercise.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.UserDetailActivity;
import com.netease.study.exercise.bean.Comment;
import com.netease.study.exercise.utils.DateHelper;
import com.netease.study.exercise.widget.FlowLayout;

import java.util.List;

/**
 * Created by netease on 15/7/28.
 * Beifeng bwifeng@163.com
 */
public class DetailCommentAdapter extends BaseAdapter {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Comment> mList;
    private String mThumb = "?imageView&thumbnail=100x100";
    private boolean mIsLocal = false;
    private int mSize = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    public DetailCommentAdapter(Context context, List<Comment> list) {
        super();
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object obj = getItem(position);
        Comment list = (Comment) obj;
        CommentHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_lost_detail, null);
            holder = new CommentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentHolder) convertView.getTag();
        }

        fillInView(convertView, list, holder);

        return convertView;
    }

    private void fillInView(View v, final Comment comment, final CommentHolder holder) {
        if (v == null || comment == null) {
            return;
        }
        if (!TextUtils.isEmpty(comment.getAvatar())) {
            Glide.with(mContext).load(comment.getAvatar()).into(holder.mHeadImg);
        }

        holder.mCommentUsname.setText(comment.getUserName());
        holder.mCommentTime.setText(DateHelper.getInstance().getRencentTime(comment.getCreateTime()));
        holder.mCommentContent.setText(comment.getContent());
        holder.mPhotoName = comment.getPhotoName();
        v.setTag(R.id.comment_list_headImg, comment.getCommentId());
        v.setTag(R.id.comment_list_usname, comment.getUserName());
        holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailActivity.launch(mContext, comment.getUserId());
            }
        });

        if (!TextUtils.isEmpty(holder.mPhotoName)) {
            String url;
            String[] imgs = holder.mPhotoName.split(",");
            holder.mFlImages.setVisibility(View.VISIBLE);
            holder.mFlImages.removeAllViews();

            for (int i = 0; i < imgs.length; i++) {
                ImageView view = (ImageView) mInflater.inflate(R.layout.draweeview_images, holder.mFlImages, false);
                url = imgs[i];
                if (mIsLocal) {
                    Glide.with(mContext).load(Uri.parse(url))
                            .fitCenter()
                            .override(mSize, mSize)
                            .into(view);
                } else {
                    Glide.with(mContext).load(Uri.parse(url + mThumb)).into(view);
                }

                final int imgPosition = i;
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO 查看大图
                    }
                });

                holder.mFlImages.addView(view);
            }
        } else {
            holder.mFlImages.setVisibility(View.GONE);
        }
    }

    public static class CommentHolder {
        private ImageView mHeadImg;
        private TextView mCommentUsname;
        private TextView mCommentTime;
        private TextView mCommentContent;
        private FlowLayout mFlImages;
        private String mPhotoName;

        public CommentHolder(View convertView) {
            mHeadImg = (ImageView) convertView.findViewById(R.id.comment_list_headImg);
            mCommentUsname = (TextView) convertView.findViewById(R.id.comment_list_usname);
            mCommentTime = (TextView) convertView.findViewById(R.id.comment_list_time);
            mCommentContent = (TextView) convertView.findViewById(R.id.comment_list_content);
            mFlImages = (FlowLayout) convertView.findViewById(R.id.images_flowlayout);
        }
    }

    public boolean isLocal() {
        return mIsLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.mIsLocal = isLocal;
    }
}
