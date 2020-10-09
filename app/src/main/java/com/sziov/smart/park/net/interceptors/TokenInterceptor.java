package com.sziov.smart.park.net.interceptors;

import com.sziov.smart.park.manager.TokenManage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: HeadInterceptor
 * @Author: willkong
 * @Description: 请求头拦截器
 * @CreateDate: 2020/7/24 1:33
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //"login-android"
        String url = request.url().toString();
        if (!url.contains("login-android")) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("androidToken", TokenManage.getToken())
                    .build();
        }
        return chain.proceed(request);
    }
}
