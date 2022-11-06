package com.tutu.common.halder;

import cn.hutool.core.convert.Convert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户信息获取
 */

public class UserInfoHandler {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();
    private static final String EMPTY = "";
    private static final String DETAILS_USER_ID = "userId";
    private static final String DETAILS_USERNAME = "userName";

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? EMPTY : value);
    }

    public static String get(String key) {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, EMPTY));
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return (T) (map.getOrDefault(key, null));
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    /**
     * 移除
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }


    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUserId() {
        return Convert.toStr(get(DETAILS_USER_ID), EMPTY);
    }

    /**
     * 设置用户Id
     *
     * @param userId
     */
    public static void setUserId(String userId) {
        set(DETAILS_USER_ID, userId);
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUserName() {
        return get(DETAILS_USERNAME);
    }

    /**
     * 设置用户名
     * @param userName
     */
    public static void setUserName(String userName) {
        set(DETAILS_USERNAME, userName);
    }

}
