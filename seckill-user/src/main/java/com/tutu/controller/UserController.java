package com.tutu.controller;

import com.tutu.common.entity.PtUser;
import com.tutu.common.halder.PtUserContextHolder;
import com.tutu.common.response.BaseResponse;
import com.tutu.service.SeUserService;
import io.swagger.annotations.Api;
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

    @GetMapping("/getInfo")
    public BaseResponse findUserInfo() {
        PtUser ptUser = PtUserContextHolder.get();
        return BaseResponse.success(ptUser);
    }

    @GetMapping("/getInfoByPhone")
    public BaseResponse getInfoByPhone(@RequestParam String phone) {
        return BaseResponse.success(userService.findUserByPhone(phone));
    }
}
