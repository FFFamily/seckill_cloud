package com.tutu.common.enums;

/**
 * Redis 常用键值枚举
 */
public interface RedisEnums {
    /**
     * 对同一时间多次重复请求做限制
     */
    public String FREQUENCY_LIMIT_KEY = "limit_time_";
    /**
     * 对ip限制
     */
    public String IP_LIMIT = "ip_limit_";
    /**
     * ip限制结束符
     */
    public String IP_LIMIT_END = "_end";
    /**
     * 活动加锁限制
     */
    public String ACTIVITY_ID = "activity_";
    /**
     * 秒杀活动
     */
    public String SECKILL_ACTIVITY = "seckill_activity_";
    /**
     * 秒杀活动库存
     */
    public String DECKILL_ACT_NUM = "seckill_act_num_";
    /**
     * 限制用户多次参与秒杀
     */
    public String USER_JOIN_LIMIT = "se_";

    /**
     * 购买数量
     */
    public Integer BUY_NUM = 1;
}
