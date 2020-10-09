package com.sziov.smart.park.netstate;

import java.lang.reflect.Method;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/21 18:27
 * @Description: 保存接受状态变化的方法对象  收集起需要接受网络状态变化的方法对象
 */
public class NetWorkStateReceiverMethod {
    /**
     * 网络改变执行的方法
     */
    Method method;
    /**
     * 网络改变执行的方法所属的类
     */
    Object object;
    /**
     * 监听的网络改变类型
     */
    NetWorkState[] netWorkState = {NetWorkState.GPRS, NetWorkState.WIFI, NetWorkState.NONE};

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public NetWorkState[] getNetWorkState() {
        return netWorkState;
    }

    public void setNetWorkState(NetWorkState[] netWorkState) {
        this.netWorkState = netWorkState;
    }
}
