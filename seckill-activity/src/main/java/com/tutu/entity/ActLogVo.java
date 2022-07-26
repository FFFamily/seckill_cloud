package com.tutu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: ActLogVo
 * @Description:
 * @author: dk
 * @date: 2022/4/6 5:37 下午
 */
@Data
public class ActLogVo {


    @ApiModelProperty("活动实际收入")
    private String payMoney;

    @ApiModelProperty("活动预计收入")
    private String allMoney;

    @ApiModelProperty("活动参与人数")
    private String userNum;

    @ApiModelProperty("活动库存")
    private String actNum;
}
