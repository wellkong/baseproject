package com.sziov.smart.park.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sziov.smart.park.app.AppConfigEntrance;
import com.sziov.smart.park.net.exception.ResponeThrowable;
import com.sziov.smart.park.net.exception.RetrofitException;
import com.sziov.smart.park.netstate.NetStateUtils;
import com.sziov.smart.park.netstate.NetWorkMonitorManager;
import com.sziov.smart.park.netstate.NetWorkState;
import com.sziov.smart.park.utils.StatusBarUtil;
import com.sziov.smart.park.utils.dialog.DialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ProjectName: autopilot-testing-app
 * @Package: com.sziov.autopilot.mvp.base
 * @Author: willkong
 * @CreateDate: 2020/5/14 12:36
 * @Description: BaseMvpActivity框架基类
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    protected P mPresenter;
    private DialogUtils dialogUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBar();
        unbinder = ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachMvpView((V) this);
        initData(savedInstanceState);
    }

    /**
     * 此处设置沉浸式地方
     */
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(this, 50);
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract P createPresenter();

    @Override
    protected void onStart() {
        super.onStart();
        //网络状态监听
        NetWorkMonitorManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMvpView();
        }
        NetWorkMonitorManager.getInstance().unregister(this);
    }

    /**
     * 弹出网络异常弹窗
     */
    public void showNoNetDialog() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils(this);
        }
        dialogUtils.showDialog("网络异常", "网络异常，请检查网络", "设置", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置
                NetStateUtils.openWirelessSettings(AppConfigEntrance.getApplicationContext());
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                dialogUtils.dismissDialog();
            }
        });
    }

    /**
     * 取消弹框
     */
    public void hideDialog() {
        if (dialogUtils != null) {
            dialogUtils.dismissDialog();
        }
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    //不加注解默认监听所有的状态，方法名随意，只需要参数是一个NetWorkState即可
//    @NetWorkMonitor(monitorFilter = {NetWorkState.NONE})//只接受网络状态变为GPRS类型的消息
    public void onNetWorkStateChange(NetWorkState netWorkState) {
        Log.i("TAG", "onNetWorkStateChange >>> :" + netWorkState.name());
        if (netWorkState == NetWorkState.NONE) {
            showNoNetDialog();
        } else {
            hideDialog();
        }
    }
}
