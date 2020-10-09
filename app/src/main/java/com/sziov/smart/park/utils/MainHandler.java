package com.sziov.smart.park.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @Author: willkong
 * @CreateDate: 2019/6/21 10:20
 * @Description: 主线程handler
 * MainHandler.getInstance().post(new Runnable() {
 * @Override public void run() {
 * // your code
 * }
 * });
 */
public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
