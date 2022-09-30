package com.tutu.controller;


import com.tutu.common.response.BaseResponse;
import com.tutu.entity.SeActivity;
import com.tutu.service.ActivityService;
import com.tutu.vo.ActivityListParamVo;
import com.tutu.vo.ActivityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀活动
 *
 * @author 涂涂
 * @DateTime: 2022/1/5 20:50
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/activity")
@Api("秒杀活动管理")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @ApiOperation("创建秒杀活动")
    @PostMapping("/create")
    public BaseResponse createActivity(@RequestBody @Validated ActivityVo vo) {
        activityService.save(vo);
        return BaseResponse.SUCCESS;
    }

    @ApiOperation("删除秒杀活动")
    @PostMapping("/delete/{id}")
    public BaseResponse delActivity(@PathVariable(value = "id") String id) {
        return BaseResponse.success(activityService.deleteById(id));
    }

    @ApiOperation("查看所有的活动活动列表")
    @PostMapping("/list")
    public BaseResponse getAllActivitys(ActivityListParamVo vo) {
        return BaseResponse.success(activityService.findAllActivities(vo));
    }

    @ApiOperation("查看具体的活动")
    @GetMapping("/getAct/{id}")
    public BaseResponse<SeActivity> getAct(@PathVariable(value = "id") String actId) {
        return BaseResponse.success(activityService.findActivityById(actId));
    }
//
//    @ApiOperation("预先装载数据")
//    @PostMapping("/beforePut")
//    public BaseResponse beforePutData(@RequestBody ActivityPutVo vo) {
//        return BaseResponse.success(activityService.putData(vo));
//    }
//
//    @ApiOperation("管理活动链接暴露")
//    @PostMapping("/expose")
//    public BaseResponse expose(@RequestBody ActivityPutVo vo) {
//        return BaseResponse.success(activityService.expose(vo));
//    }
}
