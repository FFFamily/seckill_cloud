package com.tutu.vo;

import com.tutu.common.entity.LimitVo;
import com.tutu.entity.Activity;
import lombok.Data;

/**
 * 查询活动集合参数
 */
@Data
public class ActivityListParamVo extends LimitVo<Activity> {
    /**
     * 商品编号
     */
    private String comId;

    /**
     * 活动名称
     */
    private String actTitle;

}
