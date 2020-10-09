package com.sziov.smart.park.manager;

/**
 * @ClassName: TokenManage
 * @Author: willkong
 * @Description: token 管理类
 * @CreateDate: 2020/7/24 0:57
 */
public class TokenManage {

    public static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenManage.token = token;
    }
}
