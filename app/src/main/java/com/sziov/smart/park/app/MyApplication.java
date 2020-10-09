package com.sziov.smart.park.app;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.sziov.smart.park.constant.AppUrl;
import com.sziov.smart.park.icon.FontEcModule;
import com.sziov.smart.park.net.interceptors.LoggingInterceptor;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.app
 * @Author: willkong
 * @CreateDate: 2020/9/29 10:46
 * @Description: 主程序入口类
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigEntrance.init(this)
                .withIcons(new FontAwesomeModule())
                .withIcons(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost(AppUrl.baseUrl)
                .withInterceptor(new LoggingInterceptor())
                .initNetWorkMonitorManager(this)
                .configure();
    }
}
