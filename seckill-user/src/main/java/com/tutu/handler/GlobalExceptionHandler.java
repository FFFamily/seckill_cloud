package com.tutu.handler;

import com.tutu.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse handle(Exception e) {
        e.printStackTrace();
        return BaseResponse.error(e.getMessage());
    }
}
