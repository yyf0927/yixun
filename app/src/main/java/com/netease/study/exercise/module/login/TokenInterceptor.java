package com.netease.study.exercise.module.login;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zw on 16/10/26.
 */
public class TokenInterceptor implements Interceptor {

    private String mToken;

    public TokenInterceptor(String token) {
        this.mToken = token;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("mobile", "android").addHeader("token", mToken)
                .method(original.method(), original.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
