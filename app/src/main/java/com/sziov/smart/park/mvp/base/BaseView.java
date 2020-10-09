package com.sziov.smart.park.mvp.base;

import com.sziov.smart.park.net.exception.RetrofitException;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/14 11:28
 * @Description: mvp的view基类
 */
public interface BaseView {

    /**
     * 弹出消息
     *
     * @param msg msg
     */
    void showMsg(String msg);
}
