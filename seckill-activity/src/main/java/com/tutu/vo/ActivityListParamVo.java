package com.tutu.vo;

import com.tutu.common.entity.LimitVo;
import com.tutu.entity.SeActivity;
import lombok.Data;

/**
 * 查询活动集合参数
 */
@Data
public class ActivityListParamVo extends LimitVo<SeActivity> {
    /**
     * 商品编号
     */
    private String comId;

    /**
     * 活动名称
     */
    private String actTitle;

}
