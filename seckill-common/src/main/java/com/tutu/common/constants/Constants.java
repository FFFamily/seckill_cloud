package com.tutu.common.constants;

/**
 * 全局使用常量
 */
public interface Constants {
    /**
     * Http成功状态码
     */
    String SUCCESS = "1";
    /**
     * Http失败状态码
     */
    String ERROR = "-1";

    /**
     * 令牌
     */
    String TOKEN = "TOKEN";
    /**
     * 令牌替换品
     */
    String USER_ID = "userId";
    /**
     * 后台接口前缀
     */
    String ADMIN_INTERFACE_PREFIX = "/admin";
    /**
     * 用户端接口前缀
     */
    String WEB_INTERFACE_PREFIX = "/web";
    /**
     * 公开接口前缀
     */
    String COM_INTERFACE_PREFIX = "/com";
    /**
     * 逻辑是
     */
    String YES = "1";
    /**
     * 逻辑否
     */
    String NO = "0";
}
