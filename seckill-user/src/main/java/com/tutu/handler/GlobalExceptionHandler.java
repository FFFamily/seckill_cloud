package com.tutu.handler;

import com.tutu.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse handle(Exception e){
        System.out.println(e.getMessage());
        return BaseResponse.error(e.getMessage());
    }
}
