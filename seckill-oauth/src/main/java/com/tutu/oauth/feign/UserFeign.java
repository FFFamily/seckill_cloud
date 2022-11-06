package com.tutu.oauth.feign;

import com.tutu.common.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-server")
public interface UserFeign {
    /**
     * 远程获取用户通过手机号
     * @param phone
     * @return
     */
    @GetMapping("/user/getUserByPhone/{phone}")
    BaseResponse getUserByUserPhone(@PathVariable("phone") String phone);
}
