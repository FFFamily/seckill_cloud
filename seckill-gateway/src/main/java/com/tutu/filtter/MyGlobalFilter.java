package com.tutu.filtter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import com.tutu.common.constants.Constants;
import com.tutu.common.response.BaseResponse;
import com.tutu.common.utils.RedisUtil;
import com.tutu.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 全局过滤器
 * 实现一个全局过滤器AuthGlobalFilter，当鉴权通过后将JWT令牌中的用户信息解析出来，然后存入请求的Header中，这样后续服务就不需要解析JWT令牌了，可以直接从请求的Header中获取到用户信息
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    /**
     * Token名称
     */
    private final static String TOKEN_NAME = HttpHeaders.AUTHORIZATION;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 网关拦截规则
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        log.info("网关拦截用户请求：{}", path);
        // 1. 白名单过滤
        if (checkIgnoreUrl(path)) {
            return chain.filter(exchange);
        }
        // 2，判断是否携带token
        if (!hasToken(exchange)) {
            return error(exchange, "用户未登录",null);
        }
        // 3, 如果携带了token，则拼接token
        String token = getToken(exchange);
        // 4, 拿到真实的token
        String realToken = token.replace("Bearer ", "");
        // 解析
        JWSObject jwsObject;
        try {
            jwsObject = JWSObject.parse(realToken);
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException("解析令牌出错");
        }
        Map<String, Object> userInfo = jwsObject.getPayload().toJSONObject();
        // 用户Id =》 作为Redis中的存储Key
        String userKey = (String) userInfo.get("id");
        // 判断是否在Redis中，是否已登录
        if (!redisUtil.hasKey(userKey)) {
            return error(exchange, "登录状态已过期，请重新登录",null);
        }
        // 添加到请求头中
        String userName = userInfo.get("userName").toString();
        String userId = userInfo.get("userId").toString();
        addHeader(mutate,"userName",userName);
        addHeader(mutate,"userId",userId);
        // 放行重构之后的request
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }


    /**
     * 过滤顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }


    /**
     * 判断是否携带token
     *
     * @param exchange
     * @return
     */
    private boolean hasToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_NAME);
        return StrUtil.isNotBlank(token);
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
     * 白名单过滤
     *
     * @param url
     * @return
     */
    private Boolean checkIgnoreUrl(String url) {
        List<String> urls = ignoreUrlsConfig.getUrls();
        return urls.contains(url);
    }

    /**
     * 生成响应返回
     *
     * @param exchange
     * @param msg
     * @return
     */
    private Mono<Void> error(ServerWebExchange exchange, String msg,Object object) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        ServerHttpResponse response = exchange.getResponse();
        HttpStatus status = HttpStatus.OK;
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        BaseResponse<Object> error = BaseResponse.error(msg, object);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(error).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * 添加到请求头
     *
     * @param mutate
     * @param name
     * @param value
     */
    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = null;
        try {
            URLEncoder.encode(valueStr, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            valueEncode = "";
        }
        mutate.header(name, valueEncode);
    }

}
