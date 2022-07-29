package com.tutu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品前端传输类
 */
@Data
public class SeCommodityVo implements Serializable {
    /**
     * 商品编号
     */
    private String id;
    /**
     * 商品名称
     */
    private String comName;
    /**
     * 商品描述
     */
    private String comDescribe;
    /**
     * 商品库存
     */
    private Integer comStock;
    /**
     * 商品销量
     */
    private Integer comSales;
    /**
     * 商品价格
     */
    private BigDecimal comMoney;
}
