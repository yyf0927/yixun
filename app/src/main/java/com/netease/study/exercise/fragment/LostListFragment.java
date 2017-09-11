package com.netease.study.exercise.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.netease.study.exercise.R;
import com.netease.study.exercise.adapter.LostInfoRecyclerAdapter;
import com.netease.study.exercise.bean.LostListBean;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.module.geo.GeoManager;
import com.netease.study.exercise.module.lostlist.LostListAction;
import com.netease.study.exercise.widget.recyclerview.NovaLoader;

import java.util.List;

import static com.netease.study.exercise.utils.ExerciseUtils.dp2px;

public class LostListFragment extends BaseFragment {
    private static final int PAGE_SIZE = 20;

    private SuperSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private Pull mPull = new Pull();
    private Push mPush = new Push();
    private LostInfoRecyclerAdapter mAdapter;
    private int mPageNum = -1;

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int mSpaceX;
        private int mSpaceY;

        public SpacesItemDecoration() {
            mSpaceX = dp2px(10);
            mSpaceY = dp2px(15);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpaceX;
            outRect.right = mSpaceX;
            outRect.bottom = mSpaceY;
            outRect.top = 0;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpaceY;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_list, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // refresh view
        mSwipeRefreshLayout = (SuperSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        // recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // create adapter
        mAdapter = new LostInfoRecyclerAdapter(getContext());

        setupRefreshView();
        setupRecyclerView();

        initLoad();
    }

    private void setupRefreshView() {
        mSwipeRefreshLayout.setHeaderView(mPull.createView());
        mSwipeRefreshLayout.setFooterView(mPush.createView());
        mSwipeRefreshLayout.setOnPullRefreshListener(mPull);
        mSwipeRefreshLayout.setOnPushLoadMoreListener(mPush);
        mSwipeRefreshLayout.setTargetScrollWithLayout(true);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration());
    }

    private void initLoad() {
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPull.onRefresh();
            }
        }, 200);
    }

    //
    // loader
    //

    private LostListBean loadPage(int pageNum) {
        GeoManager geoManager = GeoManager.getInstance(getContext());

        if (geoManager.hasGeo()) {
            return LostListAction.getLostList(pageNum, PAGE_SIZE, geoManager.getGeoLng(), geoManager.getGeoLng());
        } else {
            return LostListAction.getLostList(pageNum, PAGE_SIZE);
        }
    }

    private void pullLoad() {
        new PullLoader(getContext()).forceLoad();
    }

    private void pushLoad() {
        new PushLoader(getContext()).forceLoad();
    }

    private void disableLoadMore(boolean enable) {

    }

    // pull refresh control
    private class Pull implements SuperSwipeRefreshLayout.OnPullRefreshListener {
        private ProgressBar progressBar;
        private TextView textView;
        private ImageView imageView;

        public View createView() {
            View headerView = LayoutInflater.from(mSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);

            progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
            textView = (TextView) headerView.findViewById(R.id.text_view);
            imageView = (ImageView) headerView.findViewById(R.id.image_view);

            textView.setText(R.string.ptr_pull_refresh);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.down_arrow);
            progressBar.setVisibility(View.GONE);

            return headerView;
        }

        @Override
        public void onRefresh() {
            textView.setText(R.string.ptr_pull_refreshing);
            imageView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            pullLoad();
        }

        public void done() {
            mSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPullDistance(int distance) {
            // pull distance
        }

        @Override
        public void onPullEnable(boolean enable) {
            textView.setText(enable ? R.string.ptr_pull_enable : R.string.ptr_pull_disable);
            imageView.setVisibility(View.VISIBLE);
            imageView.setRotation(enable ? 180 : 0);
        }
    }

    // push refresh
    private class Push implements SuperSwipeRefreshLayout.OnPushLoadMoreListener {
        private ProgressBar progressBar;
        private TextView textView;
        private ImageView imageView;

        private View createView() {
            View footerView = LayoutInflater.from(mSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);

            progressBar = (ProgressBar) footerView.findViewById(R.id.footer_pb_view);
            imageView = (ImageView) footerView.findViewById(R.id.footer_image_view);
            textView = (TextView) footerView.findViewById(R.id.footer_text_view);

            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.down_arrow);
            textView.setText(R.string.ptr_push_refresh);

            return footerView;
        }

        @Override
        public void onLoadMore() {
            textView.setText(R.string.ptr_push_refreshing);
            imageView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            pushLoad();
        }

        public void done() {
            imageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            mSwipeRefreshLayout.setLoadMore(false);
        }

        @Override
        public void onPushEnable(boolean enable) {
            textView.setText(enable ? R.string.ptr_push_enable : R.string.ptr_push_disable);
            imageView.setVisibility(View.VISIBLE);
            imageView.setRotation(enable ? 0 : 180);
        }

        @Override
        public void onPushDistance(int distance) {
            // TODO Auto-generated method stub
        }
    }

    private class PullLoader extends NovaLoader<LostListBean> {
        public PullLoader(Context context) {
            super(context);
        }

        @Override
        public void onComplete(LostListBean data) {
            // 释放
            mPull.done();
            // 数据
            List<LostListItemBean> items = data.getSearchInfos();
            // 覆盖
            mAdapter.setItems(items);
            // 当前页
            mPageNum = 1;
            // 没有更多
            disableLoadMore(items.size() < PAGE_SIZE);
        }

        @Override
        public void onError(Throwable error) {
            // 释放
            mPull.done();
        }

        @Override
        public LostListBean loadInBackground() {
            // 第一页
            return loadPage(1);
        }
    }

    private class PushLoader extends NovaLoader<LostListBean> {
        public PushLoader(Context context) {
            super(context);
        }

        @Override
        public void onComplete(LostListBean data) {
            // 释放
            mPush.done();
            // 数据
            List<LostListItemBean> items = data.getSearchInfos();
            // 增加
            mAdapter.addItems(items);
            if (items.size() > 0) {
                // 当前页
                mPageNum = data.getPageNum();
            }
            // 没有更多
            disableLoadMore(items.size() < PAGE_SIZE);
        }

        @Override
        public void onError(Throwable error) {
            // 释放
            mPush.done();
        }

        @Override
        public LostListBean loadInBackground() {
            // 下一页
            return loadPage(mPageNum + 1);
        }
    }
}
