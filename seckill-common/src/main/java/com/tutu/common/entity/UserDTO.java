package com.tutu.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by macro on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO {
    /**
     * 编号
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
     * 状态
     */
    private Integer status;
    private List<String> roles;
}
