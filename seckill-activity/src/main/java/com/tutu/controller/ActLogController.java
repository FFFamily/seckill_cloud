package com.tutu.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@Api("管理员模块")
@RequestMapping("/log")
public class ActLogController {

//    @Resource
//    private SeActLogService seActLogService;
//
//
//    @PostMapping("/getLogByActId")
//    @ApiOperation("查询活动日志列表")
//    public BaseResponse getLogByActId(@RequestBody ActLogListParamVo vo) {
//        return BaseResponse.success(seActLogService.selectList(vo, vo.getActId()));
//    }


}
