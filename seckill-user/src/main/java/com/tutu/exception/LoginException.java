package com.tutu.exception;

public class LoginException extends RuntimeException{
    public LoginException(Exception e) {
        super(e.getMessage());
    }
}
