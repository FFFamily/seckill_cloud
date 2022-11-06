package com.tutu.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @Date 2021/12/30 14:29
 */

@Slf4j
@Component
public class RedisUtil {

    /**
     * 缓存时间
     */
    public static final int TTL_MIN_3 = 1800;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis操作异常,异常信息为 : {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 执行lua脚本
     *
     * @param text
     * @param keys
     * @return
     */
    public Object executeLua(String text, List<String> keys) {
        return null;
    }

    /**
     * 移除key
     *
     * @param key
     */
    public boolean remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("redis操作异常,异常信息为 : {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 判断是否存在key值
     * @param userKey
     * @return
     */
    public boolean hasKey(Object userKey) {
        return redisTemplate.hasKey(userKey);
    }
}
