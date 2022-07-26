package com.tutu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户参与活动列表返回对象
 *
 * @Author
 * @Date 2022/1/17 13:58
 */
@Data
public class UserJoinActListResponse implements Serializable {

    @ApiModelProperty("活动表ID")
    private String id;

    @ApiModelProperty("商品名称")
    private String comName;

    @ApiModelProperty("活动开始时间")
    private Date actStart;

    @ApiModelProperty("活动结束时间")
    private Date actEnd;

    @ApiModelProperty("活动名称")
    private String actTitle;

}
