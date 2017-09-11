package com.netease.study.exercise.module.share;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.study.exercise.R;

import java.util.List;

/**
 * Created by tangjie on 16/12/6.
 */

public class ShareListAdapter extends BaseAdapter {

    private List<ShareTarget> mTargets;

    public ShareListAdapter(@NonNull List<ShareTarget> targets) {
        mTargets = targets;
    }

    @Override
    public int getCount() {
        return mTargets.size();
    }

    @Override
    public Object getItem(int position) {
        return mTargets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view;
        if (null == convertView) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
            holder = new Holder();
            holder.mDesc = (TextView) view.findViewById(R.id.share_desc);
            holder.mLogo = (ImageView) view.findViewById(R.id.share_image_logo);
            holder.mDivider = view.findViewById(R.id.share_divider);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder)convertView.getTag();
        }
        ShareTarget target = mTargets.get(position);
        holder.mLogo.setImageResource(target.mResId);
        holder.mDesc.setText(target.mDesc);
        holder.mDivider.setVisibility(position == (mTargets.size() - 1) ? View.GONE : View.VISIBLE);
        return view;
    }

    private static class Holder {
        ImageView mLogo;
        TextView mDesc;
        View mDivider;
    }
}
