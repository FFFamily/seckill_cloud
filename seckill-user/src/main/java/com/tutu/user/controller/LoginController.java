package com.tutu.user.controller;


import com.tutu.common.response.BaseResponse;
import com.tutu.user.service.SeUserService;
import com.tutu.user.vo.LoginVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 登录
 *
 * @author 涂涂
 * @DateTime: 2022/1/5 20:48
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private SeUserService userService;

//    @Resource
//    private RedisUtil redisUtil;

    @PostMapping("/test")
    public BaseResponse test() {
        return BaseResponse.SUCCESS;
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public BaseResponse login(@RequestBody @Valid LoginVo vo) {
        return BaseResponse.success(userService.login(vo));
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public BaseResponse register(@RequestBody @Valid LoginVo vo) {
        return BaseResponse.success(userService.register(vo));
    }

//    @GetMapping("/out")
//    @ApiOperation("登出")
//    public BaseResponse logOut() {
//        boolean flag;
//        flag = redisUtil.remove(RedisKeys.getPtUserKey(PtUserContextHolder.get().getId()))
//                && redisUtil.remove(RedisKeys.getIpLimitInfo(PtUserContextHolder.get().getIp()));
//        return flag ? BaseResponse.SUCCESS : BaseResponse.ERROR;
//    }

}
