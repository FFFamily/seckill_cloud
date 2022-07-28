package com.tutu.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@Api("活动模块")
@RequestMapping("/activity")
public class ActMoneyController {

//    @Resource
//    private SeActMoneyService seActMoneyService;
//
//    @ApiOperation("查询活动日志列表")
//    public BaseResponse getActMoney(ActMoneyListParamVo vo) {
//        return BaseResponse.success(seActMoneyService.selectList(vo));
//    }
}
