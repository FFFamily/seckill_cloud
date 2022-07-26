package com.tutu.entity.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.nimbusds.jose.JWSObject;
import com.tutu.common.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@Slf4j
@Component
public class UserHandler {
    @Autowired
    private RedisTemplate redisTemplate;

    public UserDTO getCurrentUser() {
        UserDTO userDTO;
        try {
            //从Header中获取用户信息
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            JSONObject userJsonObject = new JSONObject(userStr);
            log.info("获取用户信息: {}",userJsonObject);
            userDTO = new UserDTO();
            String userName = userJsonObject.getStr("user_name");
            userDTO.setUserName(userName);
            userDTO.setId(userJsonObject.get("id").toString());
            userDTO.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userDTO;
    }
}
