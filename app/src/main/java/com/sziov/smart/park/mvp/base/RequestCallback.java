package com.sziov.smart.park.mvp.base;

import com.sziov.smart.park.net.exception.ResponeThrowable;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/14 11:28
 * @Description: 数据响应回调
 */
public interface RequestCallback<T> {
    /**
     * 请求成功
     *
     * @param data 服务器返回的结果数据
     */
    void onSuccess(T data);

    /**
     * 请求失败
     *
     * @param responeThrowable 错误信息
     */
    void onFailure(Throwable responeThrowable);
}
