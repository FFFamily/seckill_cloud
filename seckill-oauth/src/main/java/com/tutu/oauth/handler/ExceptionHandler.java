package com.tutu.oauth.handler;

import com.tutu.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public BaseResponse handler(Exception e){
        return BaseResponse.error(e.getMessage());
    }
}
