package com.tutu.common.exception;

/**
 * @Author zhangBoKai
 * @Date 2022/1/14 14:26
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }
}
