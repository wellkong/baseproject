package com.sziov.smart.park.net.exception;

/**
 * 描述：服务器下发的错误
 *
 * @author willkong
 * @date 2018/2/27
 */
public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
