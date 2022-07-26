package com.tutu.controller;

import com.example.miaosha.entity.base.BaseResponse;
import com.example.miaosha.service.SeActLogService;
import com.example.miaosha.vo.ActLogListParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin(origins = "*")
@RestController
@Api("管理员模块")
@RequestMapping("/log")
public class ActLogController {

    @Resource
    private SeActLogService seActLogService;


    @PostMapping("/getLogByActId")
    @ApiOperation("查询活动日志列表")
    public BaseResponse getLogByActId(@RequestBody ActLogListParamVo vo) {
        return BaseResponse.success(seActLogService.selectList(vo, vo.getActId()));
    }


}
