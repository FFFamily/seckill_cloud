package com.tutu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class SeActMoney implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id")
    private String actId;

    /**
     * 活动金额
     */
    @ApiModelProperty(value = "活动金额")
    private BigDecimal money;
}
