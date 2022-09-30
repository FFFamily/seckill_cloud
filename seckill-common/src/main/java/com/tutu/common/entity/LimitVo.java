package com.tutu.common.entity;


import lombok.Data;

/**
 * 分页条件查询对象
 */
@Data
public class LimitVo<T> {
    /**
     * 当前页数
     */
    private Integer pageNum = 1;

    /**
     * 显示条数
     */
    private Integer pageSize = 10;


    /**
     * 构建方法（暂时没有使用）
     * @return
     */
//    public IPage<T> getPage() {
//        return new Page<T>().setCurrent(this.pageNum).setSize(this.pageSize);
//    }
}
