package com.tutu.controller;

import com.tutu.common.response.BaseResponse;
import com.tutu.service.CommodityService;
import com.tutu.vo.SeCommodityVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商品管理
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comm")
@Api("商品管理")
public class CommodityController {
    @Resource
    private CommodityService commodityService;
    /**
     * 添加
     * @param vo
     * @return
     */
    @PostMapping("/create")
    private BaseResponse add(@RequestBody @Valid SeCommodityVo vo){
        commodityService.save(vo);
        return BaseResponse.SUCCESS;
    }
}
