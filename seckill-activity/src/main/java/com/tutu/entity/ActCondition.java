package com.tutu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动要求
 *
 * @Author
 * @Date 2022/1/17 15:27
 */
@Data
public class ActCondition implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("要求描述")
    private String con_info;


}
