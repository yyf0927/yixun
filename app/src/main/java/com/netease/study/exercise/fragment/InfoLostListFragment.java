package com.netease.study.exercise.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.OtherInfoLostListActivity;
import com.netease.study.exercise.adapter.LostListAdapter;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.module.mylostlist.LostListAction;
import com.netease.study.exercise.widget.recyclerview.NovaLoader;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;


public class InfoLostListFragment extends BaseFragment {
    private NovaRecyclerView mRecyclerView;

    private long mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserId = getActivity().getIntent().getLongExtra(OtherInfoLostListActivity.USERID, 0);
        return mRecyclerView = new NovaRecyclerView(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addPlaceholderView(dp2px(17));
        mRecyclerView.enableLoadMore();
        mRecyclerView.setAdapter(new LostListAdapter(mRecyclerView));
        mRecyclerView.setLoader(new NovaLoader<List<LostListItemBean>>(getActivity()) {
            @Override
            public void onComplete(List<LostListItemBean> data) {
                mRecyclerView.disableLoadMore();//不分页加载直接disable掉,否则等确定没有数据后再diable
                if (((LostListAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.noresult), null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (((LostListAdapter) mRecyclerView.getAdapter()).getNormalItemCount() == 0) {
                    mRecyclerView.showEmptyView(getResources().getString(R.string.loadfail), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mRecyclerView.hideEmptyView();
                            mRecyclerView.enableLoadMore();
                            mRecyclerView.load();
                        }
                    });
                }
            }

            @Override
            public List<LostListItemBean> loadInBackground() {
                return mUserId == 0 ? LostListAction.getMyLostList() : LostListAction.getOtherLostList(mUserId);
            }
        });
        mRecyclerView.load();
    }
}
