package com.sziov.smart.park.mvp.contract;

import android.app.Activity;

import com.sziov.smart.park.bean.BigSwitchScreenInfoResp;
import com.sziov.smart.park.mvp.base.BaseModel;
import com.sziov.smart.park.mvp.base.BasePresenter;
import com.sziov.smart.park.mvp.base.BaseView;
import com.sziov.smart.park.mvp.base.RequestCallback;

import java.util.List;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/18 12:05
 * @Description: 主页大屏切换契约类
 */
public interface MainContract {
    interface View extends BaseView {
        void showBigSwitchScreenList(List<BigSwitchScreenInfoResp> pageInfo);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getBigSwitchScreenList(Activity activity);
    }

    interface Model extends BaseModel {
        void getBigSwitchScreenList(Activity activity, RequestCallback<List<BigSwitchScreenInfoResp>> callback);
    }
}
