package com.netease.study.exercise.module.lostdetail;

import com.netease.study.exercise.net.HttpCallback;

/**
 * Created by vincent on 05/12/2016.
 */

public class LostDetailAction {
    public static void getLostDetail(int id, HttpCallback callback) {
        GetLostDetailHttpTask task = new GetLostDetailHttpTask(id);
        task.enqueue(callback);
    }

    public static void addComment(int id, String content, int replyCommentId, HttpCallback callback) {
        AddCommentHttpTask task = new AddCommentHttpTask(content, id, replyCommentId);
        task.enqueue(callback);
    }

    public static void getComment(int id, int pageNum, HttpCallback callback) {
        GetLostCommentHttpTask task = new GetLostCommentHttpTask(id, pageNum);
        task.enqueue(callback);
    }

    public static void addCollection(int id, HttpCallback callback){
        CollectionHttpTask task = new CollectionHttpTask(id);
        task.enqueue(callback);
    }
}
