package com.tutu.user.entity;


import lombok.Data;

/**
 * 当前用户信息
 *
 * @Author
 * @Date 2021/12/29 18:37
 */
@Data
public class PtUser {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户令牌
     */
    private String token;
    /**
     * 登录ip
     */
    private String ip;
}
