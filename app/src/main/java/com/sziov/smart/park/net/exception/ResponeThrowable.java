package com.sziov.smart.park.net.exception;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.net.exception
 * @Author: willkong
 * @CreateDate: 2020/9/29 14:47
 * @Description: 异常类
 */
public class ResponeThrowable extends Exception{
    public int code;
    public String message;

    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
