package com.netease.study.exercise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.R;
import com.netease.study.exercise.bean.Collection;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

/**
 * Created by dafei on 2017/8/28.
 */

public class CollectionAdapter extends NovaRecyclerView.NovaAdapter<Collection, NovaRecyclerView.NovaViewHolder> {
    @Override
    public NovaRecyclerView.NovaViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false));
    }

    @Override
    public void onBindNormalViewHolder(NovaRecyclerView.NovaViewHolder holder, int position) {
        ((CollectionAdapter.ViewHolder) holder).fillData(getItem(position));
    }

    private class ViewHolder extends NovaRecyclerView.NovaViewHolder implements View.OnLongClickListener{
        private TextView tv_name;
        private TextView tv_sex;
        private final String TEP_SEX_AGE = AppProfile.getContext().getResources().getString(R.string.ageNum);

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name= (TextView) itemView.findViewById(R.id.lost_list_name);
            tv_sex= (TextView) itemView.findViewById(R.id.lost_list_sex_age);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public void fillData(Collection item) {
            tv_name.setText(item.getLostName());
            tv_sex.setText(String.format(TEP_SEX_AGE, item.getLostSex() + " " + item.getYear()));

        }
    }
}
