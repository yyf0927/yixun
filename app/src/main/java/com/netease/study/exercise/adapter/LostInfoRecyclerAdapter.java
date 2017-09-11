package com.netease.study.exercise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.study.exercise.R;
import com.netease.study.exercise.bean.LostListItemBean;

import java.util.ArrayList;
import java.util.List;

public class LostInfoRecyclerAdapter extends RecyclerView.Adapter<LostInfoBaseViewHolder> {
    private final Context mContext;
    protected ArrayList<LostListItemBean> mItems = new ArrayList<LostListItemBean>();

    public LostInfoRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public LostInfoBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_lost_list_item, parent, false);
        return new LostInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LostInfoBaseViewHolder holder, int position) {
        LostInfoViewHolder textViewHolder = (LostInfoViewHolder) holder;
        textViewHolder.fillData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setItems(List<LostListItemBean> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<LostListItemBean> items) {
        int insertPosition = this.mItems.size();
        this.mItems.addAll(items);
        notifyItemRangeInserted(insertPosition, items.size());
    }

    public LostListItemBean getItem(int position) {
        return mItems.get(position);
    }

    public List<LostListItemBean> getItems() {
        return mItems;
    }
}
