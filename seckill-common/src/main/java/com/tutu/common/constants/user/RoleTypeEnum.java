package com.tutu.common.constants.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
    /**
     * 用户
     */
    USER(0),
    /**
     * 商家(管理员)
     */
    BUSINESS(1);
    private Integer value;
}
