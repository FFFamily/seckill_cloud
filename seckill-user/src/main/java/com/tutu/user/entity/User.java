package com.tutu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户表
 *
 * @Author
 * @Date 2021/12/28 16:56
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 用户角色
     */
    private Integer role;
    /**
     * 用户名 手机号
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
