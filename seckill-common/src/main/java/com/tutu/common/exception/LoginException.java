package com.tutu.common.exception;

/**
 * 登录异常
 *
 * @Author
 * @Date 2022/1/14 13:48
 */
public class LoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginException(String msg) {
        super(msg);
    }
}
