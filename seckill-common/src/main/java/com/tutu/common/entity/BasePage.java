package com.tutu.common.entity;

import lombok.Data;

import java.util.List;

/**
 * 基本分页响应对象
 * @param <T>
 */
@Data
public class BasePage<T>{
    /**
     * 总数
     */
    private Long total;
    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 页面大小
     */
    private Long PageSize;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 构造方法，传入 mybatis_plus 分页对象
     *
     * @param iPage
     */
//    public BasePage(IPage iPage) {
//        this.data = iPage.getRecords();
//        this.total = iPage.getTotal();
//        this.pageNum = iPage.getCurrent();
//        this.PageSize = iPage.getSize();
//    }

    /**
     * 静态方法，拿到自定义对象
     *
     * @param iPage
     * @return
     */
//    public static BasePage getPage(IPage iPage) {
//        return new BasePage(iPage);
//    }
}
