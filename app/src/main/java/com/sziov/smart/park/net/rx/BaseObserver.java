package com.sziov.smart.park.net.rx;

import com.sziov.smart.park.app.AppConfigEntrance;
import com.sziov.smart.park.app.ConfigKeys;
import com.sziov.smart.park.net.RestCreator;
import com.sziov.smart.park.ui.loader.AppLoader;
import com.sziov.smart.park.utils.MainHandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.net.rx
 * @Author: willkong
 * @CreateDate: 2020/9/29 15:49
 * @Description: 结果回调封装
 */

public abstract class BaseObserver<T> implements Observer<T> {

    public BaseObserver() {
    }

    public void onSubscribe(Disposable d) {

    }

    public void onNext(T t) {
        this.onSuccess(t);
    }

    public void onError(Throwable e) {
        this.onFailure(e);
    }

    @Override
    public void onComplete() {
        onRequestFinish();
    }

    /**
     * 隐藏加载进度圈
     */
    private void onRequestFinish() {
        final long delayed = AppConfigEntrance.getConfiguration(ConfigKeys.LOADER_DELAYED);
        MainHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                RestCreator.getParams().clear();
                AppLoader.stopLoading();
            }
        }, delayed);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);
}