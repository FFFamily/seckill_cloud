package com.tutu.handler;

import com.tutu.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关异常拦截
 */
@ControllerAdvice
@RestController
public class GateExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse handler(Exception e){
        return BaseResponse.error(e.getMessage());
    }
}
