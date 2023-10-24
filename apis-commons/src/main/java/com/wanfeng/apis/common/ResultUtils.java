package com.wanfeng.apis.common;

public class ResultUtils {

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 成功  传入data，自己设置成功信息message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data,String message) {
        return new BaseResponse<>(0, data, message);
    }
    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(0, null, message);
    }
    /**
     * 失败  根据错误码返回code与message
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败 自己返回code 与message
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败 返回定义的错误码code，message自己书写
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
