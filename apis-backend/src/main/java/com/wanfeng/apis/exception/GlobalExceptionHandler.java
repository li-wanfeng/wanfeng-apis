package com.wanfeng.apis.exception;


import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    //自定义的异常处理器
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse<String> businessExceptionHandler(BusinessException businessException){
        return ResultUtils.error(businessException.getCode(),businessException.getMessage());
    }
//    @ExceptionHandler(value = RuntimeException.class)
//    public BaseResponse<String> runtimeExceptionHandler(RuntimeException e){
//        return ResultUtils.error(ErrorCode.RUNTIME_ERROE);
//    }
}
