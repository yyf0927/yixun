package com.netease.study.exercise.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.study.exercise.AppProfile;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.UserDetailActivity;
import com.netease.study.exercise.bean.Friend;
import com.netease.study.exercise.module.persondetail.PersonDetailAction;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.utils.image.ImageUtils;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

/**
 * Created by netease on 16/12/2.
 */

public class FriendListAdapter extends NovaRecyclerView.NovaAdapter<Friend, NovaRecyclerView.NovaViewHolder> {
    @Override
    public NovaRecyclerView.NovaViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_system_list_item, parent, false));
    }

    @Override
    public void onBindNormalViewHolder(NovaRecyclerView.NovaViewHolder holder, int position) {
        ((ViewHolder) holder).fillData(getItem(position));
    }

    private class ViewHolder extends NovaRecyclerView.NovaViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView mContactHead;
        private TextView mContactNickname;
        private TextView mUnreadnumbertip;

        public ViewHolder(View v) {
            super(v);
            mContactHead = (ImageView) v.findViewById(R.id.contact_Head);
            mContactNickname = (TextView) v.findViewById(R.id.contact_Nickname);
            mUnreadnumbertip = (TextView) v.findViewById(R.id.unread_number_tip);
            mUnreadnumbertip.setVisibility(View.GONE);
            itemView.setOnLongClickListener(this);
            mContactHead.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        public void fillData(Friend friend) {
            ImageUtils.setUrl(mContactHead, friend.getPhotoUrl());
            mContactNickname.setText(friend.getUserName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Friend friend = getItem(position);
            switch (v.getId()) {
                case R.id.container:
                case R.id.contact_Head:
                    UserDetailActivity.launch(v.getContext(), friend.getUserId());
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            final Friend friend = getItem(position);

            final String items[] = {AppProfile.getContext().getResources().getString(R.string.delete)};
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.choose_operation);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    switch (which) {
                        case 0:
                            deleteFriend(friend);
                            break;
                    }
                }
            });
            builder.create().show();
            return false;
        }

        private void deleteFriend(final Friend friend) {
            PersonDetailAction.friendOperate(false, friend.getUserId(), new PersonDetailAction.IFollowCB() {
                @Override
                public void onSuccess() {
                    UIUtils.showToast(R.string.delete_success);
                    getItems().remove(friend);
                    notifyDataSetChanged();
                    if (getNormalItemCount() == 0) {
                        showEmptyView(AppProfile.getContext().getResources().getString(R.string.noresult), null);
                    }
                }

                @Override
                public void onFail(int code, String message) {
                    UIUtils.showToast(R.string.delete_fail);
                }
            });
        }
    }
}
