package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author
 * @Date 2021/12/28 17:01
 */
@Data
@TableName("se_activity")
public class SeActivity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("秒杀活动表ID")
    private String id;

//    @ApiModelProperty("商品id")
//    private String comId;

//    @ApiModelProperty("商品名称")
//    private String comName;

    @ApiModelProperty("秒杀数量")
    private Integer actNum;

    @ApiModelProperty("活动价格")
    private BigDecimal actMoney;

    @ApiModelProperty("活动名称")
    private String actTitle;

    @ApiModelProperty("活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT")
    private Date actStart;

    @ApiModelProperty("活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date actEnd;

    @ApiModelProperty("活动状态（1：启用,0：关闭）")
    private Integer state;

    @ApiModelProperty("活动条件ID")
    private String actConditionId;

    @ApiModelProperty("活动须知")
    private String actKnow;

    @ApiModelProperty("活动详情")
    private String actInfo;

    @ApiModelProperty("创建者编号")
    private String createBy;

    @ApiModelProperty("创建者名称")
    private String createByName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("逻辑删除（1删除/0未删除）")
    private Boolean isDeleted;

    @ApiModelProperty(value = "允许参加活动的年龄")
    private Integer limitYear;

    @ApiModelProperty(value = "允许参加的工作状态(1:无业/2:失业/3:就业/4：不做限制)")
    private Integer workState;

    @ApiModelProperty(value = "允许参加的失信状态(1:未纳入失信名单/2:纳入失信名单/3：不做限制)")
    private Integer promiseState;

    @ApiModelProperty(value = "允许参加活动的最大逾期次数")
    private Integer loanTime;

    @ApiModelProperty("是否开启筛选（1开启/0未开启）")
    private Integer isFitter;

    @ApiModelProperty("是否开启链接暴露（1开启/0未开启）")
    private Integer isOpen;
}
