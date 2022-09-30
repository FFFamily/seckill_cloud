package com.tutu.exception;

/**
 * 登录/注册异常
 */
public class LoginException extends RuntimeException{
    public LoginException(Exception e) {
        super(e.getMessage());
    }
}
