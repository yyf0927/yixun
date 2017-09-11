package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.adapter.FriendListAdapter;
import com.netease.study.exercise.bean.Friend;
import com.netease.study.exercise.module.friend.FriendAction;
import com.netease.study.exercise.widget.recyclerview.NovaLoader;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;

/**
 * Created by netease on 16/12/2.
 */

public class FriendsActivity extends BaseActivity {
    private NovaRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_friend);
        mRecyclerView = new NovaRecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addPlaceholderView(dp2px(17));
        mRecyclerView.enableLoadMore();
        mRecyclerView.setAdapter(new FriendListAdapter());
        mRecyclerView.setLoader(new NovaLoader<List<Friend>>(this) {
            @Override
            public void onComplete(List<Friend> data) {
                mRecyclerView.disableLoadMore();
                if (((FriendListAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.noresult), null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (((FriendListAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.loadfail), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mRecyclerView.enableLoadMore();
                            mRecyclerView.load();
                        }
                    });
                }
            }

            @Override
            public List<Friend> loadInBackground() {
                return FriendAction.getFriends();
            }
        });
        mRecyclerView.load();
        setContentView(mRecyclerView);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, FriendsActivity.class);
        context.startActivity(intent);
    }
}
