package com.netease.study.exercise.module.friend;

import com.netease.study.exercise.bean.FriendsListBean;
import com.netease.study.exercise.exception.ApiException;

import java.io.IOException;
import java.util.List;

/**
 * Created by netease on 16/12/2.
 */

public class FriendAction {
    public static List<com.netease.study.exercise.bean.Friend> getFriends() {
        try {
            return ((FriendsListBean) new GetFriendsHttpTask().execute().data).getRelation();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
