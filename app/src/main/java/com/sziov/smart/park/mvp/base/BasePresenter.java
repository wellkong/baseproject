package com.sziov.smart.park.mvp.base;

import java.lang.ref.WeakReference;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/14 11:29
 * @Description: mvp的Presenter基类 引用基类view
 */
public class BasePresenter<V extends BaseView> {
    /**
     * v层泛型引用
     */
    protected V mView;

    private WeakReference<V> weakReferenceView;

    public void attachMvpView(V view) {
        weakReferenceView = new WeakReference<>(view);
        this.mView = weakReferenceView.get();
    }


    public void detachMvpView() {
        weakReferenceView.clear();
        weakReferenceView = null;
        mView = null;
    }
}
