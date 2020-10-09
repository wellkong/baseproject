package com.sziov.smart.park.mvp.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * @Author: willkong
 * @CreateDate: 2020/9/29 15:18
 * @Description: 数据封装
 */
public class BaseResponse<T> implements Serializable {
    private T data;
    private String code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}