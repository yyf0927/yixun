package com.netease.study.exercise.exception;

/**
 * Created by netease on 16/12/1.
 */

public class ApiException extends RuntimeException {
    public ApiException(Throwable t) {
        super(t);
    }
}
