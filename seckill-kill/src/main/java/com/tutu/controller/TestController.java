package com.tutu.controller;

import com.tutu.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/seckill")
@RefreshScope
public class TestController {

    @Value("${service-url.nacos-order-service}")
    private String serverURL;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/getValue")
    public BaseResponse paymentInfo() {
        System.out.println(serverURL);
        return restTemplate.getForObject(serverURL+"/order/get", BaseResponse.class);
    }
}
