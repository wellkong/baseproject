package com.sziov.smart.park.app;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sziov.smart.park.crash.AppCrashHandler;
import com.sziov.smart.park.log.CustomLogCatStrategy;
import com.sziov.smart.park.log.LogcatHelper;
import com.sziov.smart.park.netstate.NetWorkMonitorManager;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 核心主配置入口
 */
public class Configurator {
    private static final HashMap<Object, Object> APP_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        APP_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    /**
     * 静态内部类 单例模式
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 配置状态
     */
    public final void configure() {
        initLogger();
        initIcons();
        APP_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    /**
     * 初始化网络状态切换
     * @param context
     * @return
     */
    public final Configurator initNetWorkMonitorManager(Application context) {
        NetWorkMonitorManager.getInstance().init(context);
        return this;
    }

    /**
     * 初始化日志框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new CustomLogCatStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("willkong")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    /**
     * 初始化崩溃日志保存
     *
     * @param context
     * @return
     */
    public final Configurator initAppCrashLog(Context context) {
        AppCrashHandler.getInstance().init(context);
        return this;
    }

    /**
     * 初始化日志保存本地
     *
     * @param context
     * @return
     */
    public final Configurator initLogcatHelper(Context context) {
        LogcatHelper.getInstance(context).start();
        return this;
    }

    public final Configurator withApiHost(String host) {
        APP_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        APP_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcons(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);

        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);

        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = APP_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) APP_CONFIGS.get(key);
    }
}
