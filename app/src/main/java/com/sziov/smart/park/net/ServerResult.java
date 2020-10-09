package com.sziov.smart.park.net;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.net
 * @Author: willkong
 * @CreateDate: 2020/10/9 10:19
 * @Description: 服务器约定结果
 * 服务器约定返回方式
 * code : 0
 * message : 调用成功
 * data : [{"type":0,"parkId":1,"cameraDtoList":[{"id":35,"status":1}]},{"type":0,"parkId":2,"cameraDtoList":[{"id":33,"status":1},{"id":34,"status":1}]},{"type":1,"parkId":null,"cameraDtoList":[]}]
 */
public class ServerResult {
    //服务器约定成功码
    public static final int SERVER_SUCCESS_CODE = 0;
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
}
