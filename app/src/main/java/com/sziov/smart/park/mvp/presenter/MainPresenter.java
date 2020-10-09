package com.sziov.smart.park.mvp.presenter;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.sziov.smart.park.app.AppConfigEntrance;
import com.sziov.smart.park.bean.BigSwitchScreenInfoResp;
import com.sziov.smart.park.mvp.base.RequestCallback;
import com.sziov.smart.park.mvp.contract.MainContract;
import com.sziov.smart.park.mvp.model.MainModel;
import com.sziov.smart.park.net.exception.ResponeThrowable;
import com.sziov.smart.park.netstate.NetStateUtils;

import java.util.List;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/18 12:39
 * @Description: 大屏业务处理
 */
public class MainPresenter extends MainContract.Presenter {
    /**
     * m层
     */
    private MainModel mainModel;

    /**
     * mvp模式  p层持有  v 和m 的接口引用 来进行数据的传递  起一个中间层的作用
     */
    public MainPresenter() {
        this.mainModel = new MainModel();
    }

    @Override
    public void getBigSwitchScreenList(Activity activity) {
        if (NetStateUtils.isNoNetConnected(AppConfigEntrance.getApplicationContext())) {
            mView.showMsg("没有网络，请设置网络");
            return;
        }
        mainModel.getBigSwitchScreenList(activity,new RequestCallback<List<BigSwitchScreenInfoResp>>() {
            @Override
            public void onSuccess(List<BigSwitchScreenInfoResp> data) {
                if (mView == null) return;
                mView.showBigSwitchScreenList(data);
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mView == null) return;
                mView.showMsg(throwable.getMessage());
            }
        });
    }
}
