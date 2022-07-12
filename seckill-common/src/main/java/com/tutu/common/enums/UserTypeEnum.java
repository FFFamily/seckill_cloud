package com.tutu.common.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    USER(0,"普通用户"),
    ADMIN(1,"管理人员")
    ;
    private Integer code;
    private String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
