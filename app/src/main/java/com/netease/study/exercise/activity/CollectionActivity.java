package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListAdapter;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.adapter.CollectionAdapter;
import com.netease.study.exercise.adapter.FriendListAdapter;
import com.netease.study.exercise.bean.Collection;
import com.netease.study.exercise.module.collection.CollectionAction;
import com.netease.study.exercise.widget.recyclerview.NovaLoader;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;

/**
 * Created by dafei on 2017/8/26.
 */

public class CollectionActivity extends BaseActivity {
    private NovaRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_mycollection);

        mRecyclerView = new NovaRecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addPlaceholderView(dp2px(17));
        mRecyclerView.enableLoadMore();
        mRecyclerView.setAdapter(new CollectionAdapter());
        mRecyclerView.setLoader(new NovaLoader<List<Collection>>(this){

            @Override
            public List<Collection> loadInBackground() {
                return CollectionAction.getCollections();
            }

            @Override
            public void onComplete(List<Collection> data) {
                mRecyclerView.disableLoadMore();
                if (((CollectionAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.noresult), null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (((CollectionAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.loadfail), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mRecyclerView.enableLoadMore();
                            mRecyclerView.load();
                        }
                    });
                }
            }
        });
        mRecyclerView.load();
        setContentView(mRecyclerView);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, CollectionActivity.class);
        context.startActivity(intent);
    }
}
