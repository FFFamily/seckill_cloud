package com.tutu.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单信息传输类
 */
@Data
public class OrderAddDto implements Serializable {
//    private static final long serialVersionUID = 1L;
    private String actId;
    // 用户id
    private String id;
    private Integer buyNum;
    private BigDecimal payMoney;
    // 秒杀时间
    private String time;

    public OrderAddDto(String actId, String id, Integer buyNum, BigDecimal payMoney) {
        this.actId = actId;
        this.id = id;
        this.buyNum = buyNum;
        this.payMoney = payMoney;
    }
}
