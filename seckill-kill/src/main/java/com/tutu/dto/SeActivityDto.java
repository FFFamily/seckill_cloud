package com.tutu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class SeActivityDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀活动表ID
     */
    private String id;

    /**
     * 商品id
     */
    private String comId;

    /**
     * 秒杀数量
     */
    private Integer actNum;

    /**
     * 活动价格
     */
    private BigDecimal actMoney;

    /**
     * 活动名称
     */
    private String actTitle;

    /**
     * 活动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT")
    private Date actStart;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date actEnd;

    /**
     * 活动状态（1：启用,0：关闭）
     */
    private Integer state;

    /**
     * 活动条件ID
     */
    private String actConditionId;

    /**
     * 活动须知
     */
    private String actKnow;

    /**
     * 活动详情
     */
    private String actInfo;


    /**=====以下是对参与活动的用户的条件限制，本系统只针对涉及到秒杀功能，所以弱化这些参数，只保留一个年龄限制，使得系统的核心主要在于秒杀======*/

    /**
     * 允许参加活动的年龄
     */
    private Integer limitYear;

    /**
     * 是否开启链接暴露（1开启/0未开启）
     */
    private Integer isOpen;


}
