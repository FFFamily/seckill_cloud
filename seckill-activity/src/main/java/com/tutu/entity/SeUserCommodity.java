package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zhangBoKai
 * @Date 2021/12/28 17:05
 */
@Data
public class SeUserCommodity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户秒杀活动绑定表ID
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    private String userId;
    /**
     * 活动编号
     */
    @ApiModelProperty("活动编号")
    private String actId;

    /**
     * 是否删除（1：删除/0：未删除）
     */
    @ApiModelProperty("是否删除（1：删除/0：未删除）")
    private Boolean isDeleted;
}
