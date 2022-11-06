package com.tutu.oauth.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class SeUserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 是否为管理员 0 用户 1 管理员
     */
    private Integer isAdmin;

    /**
     * 年龄
     */
    private Integer year;

    /**
     * 工作状态(1:无业/2:失业/3:就业)
     */
    private Integer workState;

    /**
     * 失信状态(1:未纳入失信名单/2:纳入失信名单)
     */
    private Integer promiseState;

    /**
     * 逾期次数
     */
    private Integer loanTime;

    /**
     * 余额
     */
    private BigDecimal money;
}
