package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tutu.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author
 * @Date 2021/12/28 17:01
 */
@Data
@TableName("se_activity")
public class Activity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("秒杀活动表ID")
    @TableId(type = IdType.UUID)
    private String id;

    @ApiModelProperty("商品id")
    private String comId;

    @ApiModelProperty("秒杀数量")
    private Integer actNum;

    @ApiModelProperty("活动价格")
    private BigDecimal actMoney;

    @ApiModelProperty("活动名称")
    private String actTitle;

    @ApiModelProperty("活动时间")
    private String actStart;

    @ApiModelProperty("活动结束时间")
    private String actEnd;

    @ApiModelProperty("活动状态（1：启用,0：关闭）")
    private Integer state;

    @ApiModelProperty("活动条件ID")
    private String actConditionId;

    @ApiModelProperty("活动须知")
    private String actKnow;

    @ApiModelProperty("活动详情")
    private String actInfo;


    /**=====以下是对参与活动的用户的条件限制，本系统只针对涉及到秒杀功能，所以弱化这些参数，只保留一个年龄限制，使得系统的核心主要在于秒杀======*/

    @ApiModelProperty(value = "允许参加活动的年龄")
    private Integer limitYear;

    @ApiModelProperty("是否开启链接暴露（1开启/0未开启）")
    private Integer isOpen;

}
