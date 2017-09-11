package com.netease.study.exercise.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.CollectionActivity;
import com.netease.study.exercise.activity.EntranceActivity;
import com.netease.study.exercise.activity.FriendsActivity;
import com.netease.study.exercise.activity.MyInfoLostListActivity;
import com.netease.study.exercise.module.login.UserProfile;

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.my_collection_rt).setOnClickListener(this);
        view.findViewById(R.id.my_publish_rt).setOnClickListener(this);
        view.findViewById(R.id.my_friends_rt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!UserProfile.isLogin()) {
            EntranceActivity.launch(getActivity());
            return;
        }
        switch (view.getId()) {
            case R.id.my_publish_rt: {
                navToMyPublish();
                break;
            }
            case R.id.my_friends_rt: {
                navToMyFriends();
                break;
            }
            case R.id.my_collection_rt:
                navToMyCollection();
                break;
        }
    }

    private void navToMyPublish() {
        MyInfoLostListActivity.launch(getContext());
    }

    private void navToMyFriends() {
        FriendsActivity.launch(getContext());
    }

    private void navToMyCollection() {
        CollectionActivity.launch(getContext());
    }
}
