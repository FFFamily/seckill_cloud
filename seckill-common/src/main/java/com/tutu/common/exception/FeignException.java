package com.tutu.common.exception;

/**
 * OpenFeign 远程调用异常
 */
public class FeignException extends RuntimeException{
    public FeignException(String str){
        super(str);
    }
}
