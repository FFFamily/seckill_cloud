package com.tutu.user.controller;

import com.tutu.common.halder.UserInfoHandler;
import com.tutu.common.response.BaseResponse;
import com.tutu.user.response.UserLoginResponse;
import com.tutu.user.service.SeUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 涂涂
 * @DateTime: 2022/1/5 20:51
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api("用户模块")
@RequestMapping("/user")
public class UserController {

    @Resource
    private SeUserService userService;

    @GetMapping("/getInfo")
    public BaseResponse<UserLoginResponse> findUserInfo() {
        log.info("获取用户信息[开始]");
        UserLoginResponse response = new UserLoginResponse();
        Map<String, Object> localMap = UserInfoHandler.getLocalMap();
        System.out.println(localMap);
        response.setId(UserInfoHandler.getUserId());
        response.setUserName(UserInfoHandler.getUserName());
        log.info("获取用户信息[结束]: {}",response);
        return BaseResponse.success(response);
    }

    @GetMapping("/getUserByPhone/{phone}")
    public BaseResponse getInfoByPhone(@PathVariable String phone) {
        return BaseResponse.success(userService.findUserByPhone(phone));
    }

    @GetMapping("/getUserByName/{userName}")
    public BaseResponse getUserByUserName(@PathVariable String userName){
        return BaseResponse.success(userService.findUserByName(userName));
    }


}
