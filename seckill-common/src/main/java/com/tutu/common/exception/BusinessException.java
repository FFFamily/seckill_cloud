package com.tutu.common.exception;

/**
 * 业务异常：负责展示到前端的错误
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }
}
