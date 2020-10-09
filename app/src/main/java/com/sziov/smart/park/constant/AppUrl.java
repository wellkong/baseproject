package com.sziov.smart.park.constant;

/**
 * @Author: willkong
 * @CreateDate: 2019/12/30 16:32
 * @Description: 服务器接口
 */
public class AppUrl {
    //base Ip
    public static String baseUrl = "http://106.55.34.20:8849/";

    /**
     * 大屏列表
     */
    public static final String BIG_SCREEN_LIST = "/v1/public/big-screen-config-info/list";

    /**
     * 设置参数
     */
    public static final String CONFIG_BIG_SCREEN_PARAMS = "/v1/public/big-screen-config-info";

    /**
     * 切换大屏
     */
    public static final String SWITCH_SCREEN = "/v1/private/big-screen-config-info/set-big-screen";
}
