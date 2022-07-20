package com.tutu.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class VodClientDefeat implements FallbackFactory {

    @Override
    public Object create(Throwable cause) {
        log.error(cause.getMessage());
        return null;
    }
}
