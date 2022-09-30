package com.tutu.common.interceptor;

import com.tutu.common.constants.AuthConstant;
import com.tutu.common.halder.UserInfoHandler;
import com.tutu.common.utils.ServletUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器：从请求头中拿到用户信息
 * todo 其实还可以做用户刷新
 */
public class HeaderUserInfoInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        // 存储用户信息
        UserInfoHandler.setUserId(ServletUtils.getHeader(request, AuthConstant.DETAILS_USER_ID));
        UserInfoHandler.setUserName(ServletUtils.getHeader(request, AuthConstant.DETAILS_USERNAME));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除 对应的 ThreadLocal
        UserInfoHandler.remove();
    }
}
