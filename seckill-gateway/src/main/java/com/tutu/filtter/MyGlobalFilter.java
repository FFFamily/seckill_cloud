package com.tutu.filtter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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
    private RedisTemplate redisTemplate;

    /**
     * 过滤规则
     * 已经在 ResourceServerConfig 配置中配置了 放行的白名单
     * 不需要在这里处理 白名单放行的问题
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("MyGlobalFilter： 拦截用户请求：{}", path);
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
            // 解析
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            String userInfo = new String(userStr.getBytes(StandardCharsets.UTF_8),"UTF-8");
            log.info("MyGlobalFilter.filter() 用户访问 : {}", userInfo);
        } catch (Exception e) {
            log.info("解析拼装Token出现问题,Token非法");
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
     * 给用户响应没有token的错误，失效，直接设置了 RestAuthenticationEntryPoint 作为错误返回结果
     *
     * @param exchange
     * @return
     */
    private Mono<Void> buildeNoAuthorizationResult(ServerWebExchange exchange) {
        /**
         *         ServerHttpResponse response = exchange.getResponse();
         *         response.getHeaders().set("Content-Type", "application/json");
         *         response.setStatusCode(HttpStatus.UNAUTHORIZED);
         *         JSONObject jsonObject = new JSONObject();
         *         jsonObject.put("code", Constants.ERROR);
         *         jsonObject.put("message", "NO Authorization,Token is Null or Error");
         *         DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
         *         return response.writeWith(Flux.just(wrap));
         */
        return null;
    }

}
