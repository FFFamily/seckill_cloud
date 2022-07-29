package com.tutu.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本实体参数封装
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建者编号
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
}
