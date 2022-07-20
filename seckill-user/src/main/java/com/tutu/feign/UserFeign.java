package com.tutu.feign;

import com.tutu.common.response.BaseResponse;
import com.tutu.dto.TokenDto;
import com.tutu.entity.JwtToken;
import com.tutu.handler.VodClientDefeat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录方法
 */
//调用的服务的名称,以及熔断后执行方法的类
@FeignClient(name = "oauth-server", fallback = VodClientDefeat.class)
public interface UserFeign {
    /**
     * 拿到token
     * @param grantType
     * @param username
     * @param password
     * @param clientSecret
     * @param clientId
     * @return
     */
    @PostMapping("/oauth/token")
    BaseResponse<TokenDto> getToken(
            @RequestParam("grant_type") String grantType, // 授权类型
            @RequestParam("username") String username, // 用户名
            @RequestParam("password") String password, // 用户的密码
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("client_id") String clientId
    );

}
