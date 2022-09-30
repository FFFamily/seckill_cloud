//package com.tutu.component;
//
//import cn.hutool.json.JSONUtil;
//import com.tutu.common.response.BaseResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.Charset;
//import java.security.Principal;
//import java.util.List;
//
///**
// * 自定义返回结果
// */
//@Slf4j
//@Component
//public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
//    @Override
//    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
//        String authorization1 = exchange.getRequest().getHeaders().getFirst("Authorization");
//        log.info(authorization1);
//        e.printStackTrace();
//        log.info("自定义返回结果：{}",e.getMessage());
//        Mono<Principal> principal = exchange.getPrincipal();
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.OK);
//        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        String body = JSONUtil.toJsonStr(BaseResponse.error(e.getMessage()));
//        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
//        return response.writeWith(Mono.just(buffer));
//    }
//}
