package com.tutu.common.utils;

import org.springframework.beans.BeanUtils;

/**
 * 对象拷贝工具类
 * 考虑到 BeanUtil 的 copyProperties 会有性能问题 ，所以提前做一层封装
 */
public class CopyUtil {
    /**
     * 对象拷贝
     * @param resource
     * @param target
     */
    public static void Copy(Object resource,Object target){
        BeanUtils.copyProperties(resource, target);
    }
}
