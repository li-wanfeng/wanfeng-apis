package com.wanfeng.apis.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 风车下跑
 * @create 2023-07-30
 * 通用返回类
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;//状态码

    private T data;//数据

    private String message;//描述  相当于description

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
