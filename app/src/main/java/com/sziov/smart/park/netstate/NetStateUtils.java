package com.sziov.smart.park.netstate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @Author: willkong
 * @CreateDate: 2020/5/21 18:30
 * @Description: 网络工具类
 */
@SuppressWarnings("deprecation")
public class NetStateUtils {
    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (manager == null) {
            return netType;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network networks = manager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(networks);
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //WIFI
                    netType = 1;
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    netType = 4;
                }
            } else {
                netType = 0;
            }
        } else {
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //NetworkInfo对象为空 则代表没有网络
            if (networkInfo == null) {
                return netType;
            }
            //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_WIFI) {
                //WIFI
                netType = 1;
            } else if (nType == ConnectivityManager.TYPE_MOBILE) {
                int nSubType = networkInfo.getSubtype();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                        (Context.TELEPHONY_SERVICE);
                //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 4;
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                        || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 3;
                    //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                        || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                        || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 2;
                } else {
                    netType = 2;
                }
            }
        }
        return netType;
    }

    /**
     * Open the settings of wireless.
     */
    public static void openWirelessSettings(Context context) {
        context.startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    /**
     * 判断是否无网络
     *
     * @param context
     * @return
     */
    public static boolean isNoNetConnected(Context context) {
        //默认网络断开
        boolean isAvailable = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            isAvailable = false;
        } else {
            if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = false;
            }
        }
        return isAvailable;
    }
}
