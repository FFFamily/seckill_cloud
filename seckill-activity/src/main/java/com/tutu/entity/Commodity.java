package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tutu.common.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品实体
 * 本系统对于商品实体信息参数也进行弱化，只展示必须字段
 */
@Data
public class Commodity extends BaseEntity {
    /**
     * 商品编号
     */
    @TableId(type = IdType.UUID)
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