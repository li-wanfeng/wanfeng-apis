package com.wanfeng.apis.common;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
public enum ErrorCode {
    SUCCESS(0,"OK"),
    RUNTIME_ERROE(50000,"系统内部错误"),
    NO_AUTH_ERROR(50100,"权限不足"),
    PARAMS_ERROR(50101,"参数错误"),
    DELETE_ERROR(50103,"删除失败"),
    UPDATE_ERROR(50104,"更新失败"),
    NOT_LOGIN_ERROR(400100,"未登录"),
    LOGIN_PARAMS_ERROR(40101,"用户名或密码错误"),
    USER_EXSIT(40102,"用户已存在"),
    USER_NOT_CREATE_ERROR(40103,"用户不存在"),
    OPERATION_ERROR(40103,"操作失败"),
    INTERFACEINTO_EXSIT(40102,"接口已存在");
    //等等状态码
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
