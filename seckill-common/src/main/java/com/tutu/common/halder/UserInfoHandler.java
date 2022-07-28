package com.tutu.common.halder;

import cn.hutool.core.lang.Assert;
import com.tutu.common.entity.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserInfoHandler {
    public UserDTO getCurrentUser() {
        UserDTO userDTO = new UserDTO();
        // 这里还需要加一层 用户非空判断
        // 校验用户
        Assert.isTrue(!Objects.isNull(userDTO), "当前用户不存在或未登录");
        return userDTO;
    }
}
