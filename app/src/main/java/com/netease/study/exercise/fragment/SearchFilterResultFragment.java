package com.netease.study.exercise.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.SearchResultActivity;
import com.netease.study.exercise.adapter.LostListAdapter;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.bean.SearchFilterBean;
import com.netease.study.exercise.module.search.SearchFilterAction;
import com.netease.study.exercise.widget.recyclerview.NovaLoader;
import com.netease.study.exercise.widget.recyclerview.NovaRecyclerView;

import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;


public class SearchFilterResultFragment extends BaseFragment {
    private final static int PAGE_SIZE = 6;

    private NovaRecyclerView mRecyclerView;

    private SearchFilterBean mSearchFilterBean;
    private int mCurrentPageNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String json = getActivity().getIntent().getStringExtra(SearchResultActivity.EXTRA);
        if (!TextUtils.isEmpty(json)) {
            mSearchFilterBean = JSON.parseObject(json, SearchFilterBean.class);
        }
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
                mCurrentPageNum++;
                if (data.size() < PAGE_SIZE) {
                    mRecyclerView.disableLoadMore();
                }
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
                return SearchFilterAction.search(mSearchFilterBean, PAGE_SIZE, mCurrentPageNum + 1).getSearchInfos();
            }
        });
        mRecyclerView.load();
    }

}
