package com.sziov.smart.park.netstate;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/21 18:24
 * @Description: NetworkCallback类来监听网络状态的变化
 */
public class NetworkCallback {
    public NetworkCallback() {
        throw new RuntimeException("Stub!");
    }

    public void onAvailable(Network network) {//网络可用的时候调用
        throw new RuntimeException("Stub!");
    }

    public void onLosing(Network network, int maxMsToLive) {//网络正在减弱，链接会丢失数据，即将断开网络时调用
        throw new RuntimeException("Stub!");
    }

    public void onLost(Network network) {//网络断开时调用
        throw new RuntimeException("Stub!");
    }

    public void onUnavailable() {//网络缺失network时调用
        throw new RuntimeException("Stub!");
    }

    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {//网络功能发生改变时调用
        throw new RuntimeException("Stub!");
    }

    //网络连接属性发生改变时调用
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        throw new RuntimeException("Stub!");
    }
}
