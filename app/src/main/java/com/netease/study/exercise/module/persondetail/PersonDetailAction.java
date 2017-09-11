package com.netease.study.exercise.module.persondetail;

import com.netease.study.exercise.bean.OtherInfoBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.net.HttpCallback;

import java.util.HashMap;

/**
 * Created by netease on 16/11/27.
 */

public class PersonDetailAction {
    public static void modify(HashMap<String, String> params, String filepath, final IModifyCB modifyCB) {
        PersonDetailHttpTask task = new PersonDetailHttpTask(params, filepath);
        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                if (modifyCB != null) {
                    modifyCB.onModifySuccess((UserBean) data);
                }
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (modifyCB != null) {
                    modifyCB.onModifyFailed(code, message);
                }
            }
        });
    }

    public static void getOtherUserDetail(long id, final IGetUserDetailCB getUserDetailCB) {
        UserDetailHttpTask task = new UserDetailHttpTask(id);
        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                if (getUserDetailCB != null) {
                    getUserDetailCB.onGetSuccess((OtherInfoBean) data);
                }
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (getUserDetailCB != null) {
                    getUserDetailCB.onGetFail(code, message);
                }
            }
        });

    }

    public static void friendOperate(boolean isFollow, long id, final IFollowCB followCB) {
        FriendOperationHttpTask task = new FriendOperationHttpTask(id, isFollow);
        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                if (followCB != null) {
                    followCB.onSuccess();
                }
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (followCB != null) {
                    followCB.onFail(code, message);
                }
            }
        });

    }

    public interface IModifyCB {
        void onModifySuccess(UserBean userBean);

        void onModifyFailed(int code, String message);
    }

    public interface IGetUserDetailCB {
        void onGetSuccess(OtherInfoBean info);

        void onGetFail(int code, String message);
    }

    public interface IFollowCB {
        void onSuccess();

        void onFail(int code, String message);
    }
}
