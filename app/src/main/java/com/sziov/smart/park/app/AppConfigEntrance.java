package com.sziov.smart.park.app;

import android.content.Context;

/**
 * 主配置入口调用类
 */
public final class AppConfigEntrance {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getAppConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
