package com.tutu.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedisKey {
    private static final String USER_KEY = "USER:{userId}";

    /**
     * 获取用户Key
     * @param userId
     * @return
     */
    public static String getUserKey(String userId){
        return populatePlaceholder(USER_KEY,userId);
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
