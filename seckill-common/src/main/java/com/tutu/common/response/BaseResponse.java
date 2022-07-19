package com.tutu.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础返回类
 *
 * @Author
 * @Date 2021/12/28 17:19
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final Integer SUCCESS_CODE = 0;
    private static final Integer ERROR_CODE = -1;
    private static final Integer WARNING_CODE = 1;
    private static final Integer AUTHORIZE_CODE = 100000;
    private static final String SUCCESS_MSG = "请求成功！";
    public static final BaseResponse<String> SUCCESS = new BaseResponse(SUCCESS_CODE, SUCCESS_MSG, null);
    private static final String ERROR_MSG = "服务器繁忙！";
    public static final BaseResponse<String> ERROR = new BaseResponse(ERROR_CODE, ERROR_MSG, null);

    private Integer code;
    private String message;
    private T data;

    public BaseResponse() {
    }

    private BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> result(Integer code, String msg, T data) {
        return new BaseResponse(code, msg, data);
    }

    public static <T> BaseResponse<T> success(T data) {
        return result(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return result(SUCCESS_CODE, message, data);
    }

    public static <T> BaseResponse<T> error(String msg) {
        return result(ERROR_CODE, msg, null);
    }

    public static <T> BaseResponse<T> error(String msg, T data) {
        return result(ERROR_CODE, msg, data);
    }

    public static <T> BaseResponse<T> warning(String msg) {
        return result(WARNING_CODE, msg, null);
    }

    public static <T> BaseResponse<T> warning(String msg, T data) {
        return result(WARNING_CODE, msg, data);
    }

    public static <T> BaseResponse<T> authorize(String msg) {
        return result(AUTHORIZE_CODE, msg, null);
    }

    public static <T> BaseResponse<T> authorize(String msg, T data) {
        return result(AUTHORIZE_CODE, msg, data);
    }
}
