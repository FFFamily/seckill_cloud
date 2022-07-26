package com.tutu.controller;

import com.example.miaosha.entity.base.BaseResponse;
import com.example.miaosha.service.SeActMoneyService;
import com.example.miaosha.vo.ActMoneyListParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@CrossOrigin(origins = "*")
@RestController
@Api("活动模块")
@RequestMapping("/activity")
public class ActMoneyController {

    @Resource
    private SeActMoneyService seActMoneyService;

    @ApiOperation("查询活动日志列表")
    public BaseResponse getActMoney(ActMoneyListParamVo vo) {
        return BaseResponse.success(seActMoneyService.selectList(vo));
    }
}
