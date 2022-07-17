package com.tutu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Oauth2 资源管理器
 * 内部关联了ResourceServerSecurityConfigurer 和 HttpSecurity。前者与资源安全配置相关，后者与http安全配置相关
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 构造方法
     */
    public ResourceServerConfig() {
        super();
    }

    /**
     * 暂时不需要配置这个
     * @param resources configurer for the resource server
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     *
     * @param http the current http filter configuration
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**") // 匹配需要资源认证的路径（这里是对所有的请求都拦截）
                .authorizeRequests() // 允许基于 HttpServletRequest 使用限制访问
                .antMatchers("/login","/oauth/rsa/publicKey")
                .permitAll(); // //匹配不需要资源认证路径,指定任何人都允许 URL。
    }
}
