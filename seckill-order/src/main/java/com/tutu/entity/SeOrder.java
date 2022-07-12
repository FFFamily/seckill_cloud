package com.tutu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单
 *
 * @Author
 * @Date 2021/12/28 17:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SeOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单表ID
     */
    private String id;
    /**
     * 订单编号
     */
    private String ordNo;
    /**
     * 所属用户id
     */
    private String userId;

    /**
     * 活动ID
     */
    private String actId;

//    /**
//     * 购买的商品id
//     */
//    @ApiModelProperty(value = "购买的商品id")
//    private String comId;
    /**
     * 购买的数量
     */
    private Integer payNum;

    /**
     * 订单时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String time;

    /**
     * 订单状态 （0已支付 / 1 未支付 ）
     */
    private int state;

    /**
     * 订单金额
     */
    private BigDecimal payMoney;


}
