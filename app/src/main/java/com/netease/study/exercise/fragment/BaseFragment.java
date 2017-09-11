package com.netease.study.exercise.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by netease on 16/11/13.
 */

public abstract class BaseFragment extends Fragment {
    protected void setTitle(String title) {
        getActivity().setTitle(title);
    }
}
