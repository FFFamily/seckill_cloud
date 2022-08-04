package com.tutu.vo;

import com.tutu.common.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 秒杀Vo
 */
@Data
public class SeckillVo extends BaseEntity {
    /**
     * 活动编号
     */
    @NotBlank(message = "活动编号不能为空")
    private String actId;

    /**
     * 购买数量
     */
    private Integer buyNum;
}
