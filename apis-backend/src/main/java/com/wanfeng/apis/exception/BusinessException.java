package com.wanfeng.apis.exception;


import com.wanfeng.apis.common.ErrorCode;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
public class BusinessException extends RuntimeException{
    private final int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }
    //根据自定义错误码去定义code 和message
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    //根据自定义错误码去定义code 和message自己输入
    public BusinessException(ErrorCode errorCode,String message){
        super(message);
        this.code = errorCode.getCode();
    }



    public int getCode() {
        return code;
    }
}
