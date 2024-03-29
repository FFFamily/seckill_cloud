package com.tutu.oauth.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.tutu.common.response.BaseResponse;
import com.tutu.common.utils.RedisKey;
import com.tutu.common.utils.RedisUtil;
import com.tutu.oauth.domain.Oauth2TokenDto;
import com.tutu.oauth.feign.OauthFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@RequestMapping("/oauth")
public class AuthController implements OauthFeign {
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Resource
    private RedisUtil redisUtil;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public BaseResponse<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        String id = additionalInformation.get("userId").toString();
        // 存储Redis 用 Md5 摘要加密
        redisUtil.set(RedisKey.getUserKey(id), DigestUtil.md5Hex(oAuth2AccessToken.getValue()),oAuth2AccessToken.getExpiresIn());
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ")
                .build();
        return BaseResponse.success(oauth2TokenDto);
    }

}
