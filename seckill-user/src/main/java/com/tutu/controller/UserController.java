package com.tutu.controller;

import com.tutu.common.entity.PtUser;
import com.tutu.common.halder.PtUserContextHolder;
import com.tutu.common.response.BaseResponse;
import com.tutu.common.utils.UserInfoUtil;
import com.tutu.handler.UserHandler;
import com.tutu.service.SeUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 涂涂
 * @DateTime: 2022/1/5 20:51
 */
@CrossOrigin(origins = "*")
@RestController
@Api("用户模块")
@RequestMapping("/user")
public class UserController {

    @Resource
    private SeUserService userService;

    @Autowired
    private UserHandler userHandler;

    @GetMapping("/getInfo")
    public BaseResponse findUserInfo() {
        return BaseResponse.success(userHandler.getCurrentUser());
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
