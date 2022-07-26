package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("se_activity_log")
public class SeActLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("查看活动记录表ID")
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("活动id")
    private String actId;

    @ApiModelProperty("查看时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String watchTime;

}
