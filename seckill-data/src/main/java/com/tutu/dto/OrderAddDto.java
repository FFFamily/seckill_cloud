package com.tutu.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class OrderAddDto implements Serializable {
    private String actId;
    // 用户id
    private String id;
    private Integer buyNum;
    private BigDecimal payMoney;
    // 秒杀时间
    private String time;
}
