package com.tutu.common.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Redis 键值
 *
 * @Author
 * @Date 2021/12/30 11:53
 */
public class RedisKeys {
    /**
     * 用户 键值
     */
    private static final String PT_USER_INFO_KEY = "PT-USER:{token}";
    /**
     * ip 键值
     */
    private static final String IP_LIMIT_INFO = "IP-LIMIT:{ip}";
    /**
     * 用户成功秒杀键值
     */
    private static final String SUCCESS_USER = "SUCCESS_USER:{user}";

    /**
     * 用户信息key
     *
     * @param token 用户token
     * @return
     */
    public static String getPtUserKey(String token) {
        return populatePlaceholder(PT_USER_INFO_KEY, token);
    }

    /**
     * ip键值
     *
     * @param ip
     * @return
     */
    public static String getIpLimitInfo(String ip) {
        return populatePlaceholder(IP_LIMIT_INFO, ip);
    }

    /**
     * 成功 键值
     *
     * @param user
     * @return
     */
    public static String getSuccessUserInfo(String user) {
        return populatePlaceholder(SUCCESS_USER, user);
    }

    /**
     * 替换占位符
     *
     * @param key    redis Key模板。模板占位符格式: {variable}
     * @param params 实际参数
     * @return
     */
    private static String populatePlaceholder(String key, Object... params) {
        // 正则表达式
        // TODO 需要有我自己的一套匹配方式
        // 匹配
        Matcher matcher = Pattern.compile("\\{.*?\\}").matcher(key);
        int index = 0;
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, String.valueOf(params[index]));
            index++;
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
