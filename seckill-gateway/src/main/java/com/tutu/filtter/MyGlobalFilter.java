package com.tutu.filtter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import com.nimbusds.jose.JWSObject;
import com.tutu.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    /**
     * Token名称
     */
    private final static String TOKEN_NAME = HttpHeaders.AUTHORIZATION;

    /**
     * 过滤规则
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("拦截用户请求：{}", path);
        // 1，判断是否携带token
        // 没有放行，进入 IgnoreUrlsRemoveJwtFilter 过滤
        if (!hasToken(exchange)) {
            return chain.filter(exchange);
        }
        // 如果携带了token，则拼接token
        try {
            String token = getToken(exchange);
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("MyGlobalFilter.filter() user:{}", userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (Exception e) {
            log.info("解析拼装Token出现问题");
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }


    /**
     * 过滤顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }


    /**
     * 判断是否携带token
     *
     * @param exchange
     * @return
     */
    private boolean hasToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_NAME);
        return StringUtils.isBlank(token) ? false : true;
    }

    /**
     * 拿到token
     *
     * @param exchange
     * @return
     */
    private String getToken(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst(TOKEN_NAME);
    }

    /**
     * 给用户响应没有token的错误，失效，直接设置了 RestAuthenticationEntryPoint 作为错误返回结果
     *
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

}
