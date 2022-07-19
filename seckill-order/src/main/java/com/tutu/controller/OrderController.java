package com.tutu.controller;

import com.tutu.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 *
 * @Author
 * @Date 2022/1/18 11:07
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
// Spring Cloud 原生注解
@RefreshScope
public class OrderController {

    @Value("${useLocalCache:true}")
    private boolean useLocalCache;

    @GetMapping("/get")
    public BaseResponse get() {
        System.out.println("test");
        return BaseResponse.success(useLocalCache);
    }


//    @Resource
//    private OrderService orderService;
//
//    /**
//     * 取消订单(秒杀活动中的订单)
//     *
//     * @param orderNo
//     * @return
//     */
//    public BaseResponse cancelOrder(String orderNo) {
//        return BaseResponse.success(orderService.cancelSeckillOrder(orderNo));
//    }
//
//    /**
//     * 添加订单
//     *
//     * @return
//     */
//    public BaseResponse addOrder() {
//        return null;
//    }
//
//    /**
//     * 更新
//     *
//     * @return
//     */
//    public BaseResponse updateOrder() {
//        return null;
//    }
//
//    /**
//     * 分页查询订单
//     *
//     * @return
//     */
//    @ApiOperation("查看订单")
//    @PostMapping("/list")
//    public BaseResponse selectOrder(@RequestBody OrderVo vo) {
//        return orderService.selectOrder(vo);
//    }
//
//    /**
//     * 查看订单详情
//     */
//    public BaseResponse findOrder() {
//        return null;
//    }
//
//
//    /**
//     * 管理员查看活动汇总
//     *
//     * @return
//     */
//    @ApiOperation("查看活动汇总")
//    @PostMapping("/selectByActId")
//    public BaseResponse selectByActId(@RequestBody OrderVo vo) {
//        return orderService.selectByActId(vo);
//    }


}
