package com.netease.study.exercise.module.uploadlost;

import com.netease.study.exercise.bean.LostPersonBean;
import com.netease.study.exercise.bean.MatchListBean;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.net.HttpCallback;

/**
 * Created by netease on 16/11/27.
 */

public class UploadLostAction {
    public static void uploadLost(LostPersonBean lostPersonBean, final IUploadLostCB uploadLostCB) {
        UploadLostHttpTask task = new UploadLostHttpTask(lostPersonBean);

        task.enqueue(new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                uploadLostCB.onUploadLostSuccess((MatchListBean) data);

            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                if (uploadLostCB != null) {
                    uploadLostCB.onUploadLostFailed(code, message);
                }

            }
        });


    }

    public interface IUploadLostCB {
        void onUploadLostSuccess(MatchListBean matchListBean);

        void onUploadLostFailed(int code, String message);
    }
}
