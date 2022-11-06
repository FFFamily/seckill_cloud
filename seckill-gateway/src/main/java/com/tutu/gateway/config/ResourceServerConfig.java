//package com.tutu.config;
//
//
//import com.tutu.authorization.AuthorizationManager;
//import com.tutu.component.RestAuthenticationEntryPoint;
//import com.tutu.component.RestfulAccessDeniedHandler;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 资源服务器配置
// */
//@AllArgsConstructor
//@Configuration
////@EnableWebFluxSecurity
//public class ResourceServerConfig {
//    private final AuthorizationManager authorizationManager;
//    // 错误返回结果
//    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//    // 正确返回结果
//    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
//    // 白名单过滤拦截器
////    private final IgnoreUrlsFilter ignoreUrlsFilter;
//    // 白名单
//    private final IgnoreUrlsConfig ignoreUrlsConfig;
//
////    @Bean
////    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
////        http.oauth2ResourceServer().jwt()
////                .jwtAuthenticationConverter(null);
////        //自定义处理JWT请求头过期或签名错误的结果
////        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
////        //对白名单路径，直接移除JWT请求头
//////        http.addFilterBefore(ignoreUrlsFilter, SecurityWebFiltersOrder.AUTHENTICATION);
////        http.authorizeExchange()
////                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(),String.class)).permitAll()//白名单配置
////                .anyExchange().access(authorizationManager)//鉴权管理器配置
////                .and().exceptionHandling()
////                .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
////                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
////                .and().csrf().disable();
////        return http.build();
////    }
//
////    @Bean
////    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
////        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
////        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
////        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
////        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
////        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
////        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
////    }
//}
