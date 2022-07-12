package com.tutu.filtter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import com.tutu.common.constants.Constants;
import com.tutu.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered, ApplicationRunner {
    /**
     * 放行白名单
     */
    private Set<String> adoptUris;
    /**
     * Token名称
     */
    private final static String TOKEN_NAME = HttpHeaders.AUTHORIZATION;
    /**
     * 过滤规则
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("拦截用户请求：{}", path);
        // 1,校验白名单
        if (isRequireUri(path)){
            return chain.filter(exchange);
        }
        // 2,校验Token
        if (!isRequireToken(exchange)){
            // 没有 token 直接报错
            return buildeNoAuthorizationResult(exchange);
        }
        return buildeNoAuthorizationResult(exchange);
    }


    /**
     * 过滤顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 判断是否是白名单URL路径
     * @param path
     * @return
     */
    private boolean isRequireUri(String path) {
        // 校验放行白名单
        if (adoptUris.contains(path)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否携带token
     * @param exchange
     * @return
     */
    private boolean isRequireToken(ServerWebExchange exchange){
        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_NAME);
        return StringUtils.isBlank(token) ? false : true;
    }

    /**
     * 给用户响应没有token的错误
     * @param exchange
     * @return
     */
    private Mono<Void> buildeNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", Constants.ERROR);
        jsonObject.put("message", "NO Authorization,Token is Null or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    /**
     * 加载时启动
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("GateWay模块MyGlobalFilter启动");
        adoptUris = new HashSet<>();
        // 模块前驱变量
        String scekillModel = "/seckill";
        String userModel = "/user";
        // 白名单
        adoptUris.add(scekillModel+"/getValue");
        adoptUris.add(userModel+"/register");
    }
}
