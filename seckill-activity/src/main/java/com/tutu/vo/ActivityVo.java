package com.tutu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀活动实体
 */
@Data
public class ActivityVo {
    @ApiModelProperty("秒杀数量")
    @NotNull(message = "数量不能为空")
    private Integer actNum;

    @ApiModelProperty("活动价格")
    @NotNull(message = "活动价格不为空")
    private BigDecimal actMoney;

    @ApiModelProperty("活动时间")
    @NotNull(message = "必须有活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date actStart;

    @ApiModelProperty("活动结束时间")
    @NotNull(message = "必须有活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date actEnd;

    @ApiModelProperty("活动名称")
    @NotBlank(message = "活动名称不能为空")
    private String actTitle;

    @ApiModelProperty("活动详情")
    private String actInfo;

    @ApiModelProperty("活动须知")
    private String actKnow;


    @ApiModelProperty(value = "允许参加活动的年龄")
    private Integer limitYear;

    @ApiModelProperty(value = "允许参加的工作状态(1:无业/2:失业/3:就业/4：不做限制)")
    private Integer workState;

    @ApiModelProperty(value = "允许参加的失信状态(1:未纳入失信名单/2:纳入失信名单/3：不做限制)")
    private Integer promiseState;

    @ApiModelProperty(value = "允许参加活动的最大逾期次数")
    private Integer loanTime;

    @ApiModelProperty(value = "是否开启筛选（1开启/0未开启）")
    private Integer isFitter;

    @ApiModelProperty("是否开启链接暴露（1开启/0未开启）")
    private Integer isOpen;
}
