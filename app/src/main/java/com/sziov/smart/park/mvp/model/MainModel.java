package com.sziov.smart.park.mvp.model;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sziov.smart.park.bean.BigSwitchScreenInfoResp;
import com.sziov.smart.park.constant.AppUrl;
import com.sziov.smart.park.mvp.base.BaseResponse;
import com.sziov.smart.park.mvp.base.RequestCallback;
import com.sziov.smart.park.mvp.contract.MainContract;
import com.sziov.smart.park.net.RestUtil;
import com.sziov.smart.park.net.rx.BaseObserver;
import com.sziov.smart.park.net.rx.RxRestClient;
import com.sziov.smart.park.ui.loader.AppLoader;
import com.sziov.smart.park.utils.FastJsonUtil;

import java.util.List;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/18 12:32
 * @Description: 大屏数据请求
 */
public class MainModel implements MainContract.Model {

    @Override
    public void getBigSwitchScreenList(Activity activity, RequestCallback<List<BigSwitchScreenInfoResp>> callback) {
        RxRestClient.builder().loader(activity).url(AppUrl.BIG_SCREEN_LIST).build().get().compose(RestUtil.applySchedulers(new BaseObserver<String>() {

            @Override
            public void onSuccess(String s) {
                List<BigSwitchScreenInfoResp> bigSwitchScreenInfoRespList = FastJsonUtil.parseListResult(s,BigSwitchScreenInfoResp.class);
                if (callback!=null){
                    callback.onSuccess(bigSwitchScreenInfoRespList);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (callback!=null){
                    callback.onFailure(e);
                }
            }
        }));
    }
}
