package com.sziov.smart.park.net.interceptors;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @ClassName: RequestParamsInterceptor
 * @Author: willkong
 * @Description: 请求参数 回调 拦截器
 * @CreateDate: 2020/7/24 11:47
 */
public class LoggingInterceptor implements Interceptor {
    private String TAG = "Logging %s";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.wtf(TAG, "----------Request Start----------------");
        printParams(request);
        Logger.i(TAG, "| " + request.toString() + "===========" + request.headers().toString());
        Logger.json(content);
        Logger.i(content);
        Logger.wtf(TAG, "----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }


    /**
     * 请求参数日志打印
     *
     * @param request
     */
    private void printParams(Request request) {
        //重点部分----------针对post请求做处理-----------------------
        try {
            String method = request.method();
            if ("POST".equals(method)) {//post请求需要拼接
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Logger.i("请求报文： RequestParams:{" + sb.toString() + "}");
                }
            } else {//get请求直接打印url
                Logger.i("request params==" + request.url() + "\n 参数==" + request.body().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
