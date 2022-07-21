package com.tutu.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.tutu.common.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class UserHandler {

    public UserDTO getCurrentUser() {
        UserDTO userDTO = null;
        try {
            //从Header中获取用户信息
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String userStr = request.getHeader("user");
            JSONObject userJsonObject = new JSONObject(userStr);
            log.info("获取用户信息: {}",userJsonObject);
            userDTO = new UserDTO();
            String userName = userJsonObject.getStr("user_name");
            userDTO.setUserName(new String(userName.getBytes(),"UTF-8"));
            userDTO.setId(userJsonObject.get("id").toString());
            userDTO.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        }catch (UnsupportedEncodingException unsupportedEncodingException){
            log.warn("获取用户信息失败: {}",unsupportedEncodingException.getMessage());
        }
        return userDTO;
    }
}
