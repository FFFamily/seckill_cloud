package com.tutu.oauth.feign;

import com.tutu.common.response.BaseResponse;
import com.tutu.oauth.domain.Oauth2TokenDto;
import com.tutu.oauth.handler.VodClientDefeat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@FeignClient(name = "oauth-server", fallback = VodClientDefeat.class)
public interface OauthFeign {

    /**
     * 获取token
     * @param principal
     * @param parameters
     * @return
     */
    @PostMapping("/oauth/token")
    BaseResponse<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException;
}
