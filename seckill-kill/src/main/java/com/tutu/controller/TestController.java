package com.tutu.controller;

import com.tutu.common.response.BaseResponse;
import com.tutu.service.SeckillService;
import com.tutu.vo.SeckillVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/seckill")
//@RefreshScope
public class TestController {
    @Resource
    private SeckillService seckillService;

    /**
     * 秒杀商品
     */
    @PostMapping("/buy")
    public BaseResponse scekill(@RequestBody @Valid SeckillVo vo) {
        seckillService.seckill(vo);
        return BaseResponse.SUCCESS;
    }

}
