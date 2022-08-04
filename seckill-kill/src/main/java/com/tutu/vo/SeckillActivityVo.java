package com.tutu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class SeckillActivityVo implements Serializable {
    /**
     * 活动id
     */
    private String actId;
    /**
     * 活动商品价格
     */
    private BigDecimal price;
    /**
     * 商品库存
     */
    private Integer seActStock;
    /**
     * 开始时间
     */
    private Date start;
    /**
     * 结束时间
     */
    private Date end;
}
