package com.tutu.common.utils;

import cn.hutool.core.util.StrUtil;
import com.tutu.common.constants.TokenConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * TOKEN 处理工具类
 */
public class TokenUtil {
    /**
     * 获取请求token
     */
    public static String getToken() {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        // 从header获取token标识
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

}
