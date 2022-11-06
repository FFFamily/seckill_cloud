package com.tutu.common.config;

import com.tutu.common.interceptor.HeaderUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private HeaderUserInfoInterceptor headerUserInfoInterceptor;
    /**
     * 不需要拦截地址
     */
    public static final String[] excludeUrls = {"/user/login", "/logout", "/refresh"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerUserInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
    }

}
