package com.tutu.service;

import com.alibaba.fastjson.JSONObject;
import com.tutu.common.entity.RedisKeys;
import com.tutu.common.enums.RedisEnums;
import com.tutu.common.exception.BusinessException;
import com.tutu.common.halder.UserInfoHandler;
import com.tutu.common.utils.RedisUtil;
import com.tutu.config.MqProperties;
import com.tutu.dto.OrderAddDto;
import com.tutu.dto.SeActivityDto;
import com.tutu.feign.ActivityFeign;
import com.tutu.vo.SeckillActivityVo;
import com.tutu.vo.SeckillVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀服务
 */
@Service
public class SeckillService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ActivityFeign activityFeign;
    @Resource
    private UserInfoHandler userInfoHandler;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MqProperties mqProperties;
    /**
     * 秒杀方法
     * @param vo
     */
    public void wseckill(SeckillVo vo) {
        // 秒杀流程开始
        // 1.过滤-非参与秒杀活动的用户不能参加秒杀活动，这个功能通过前端控制
        // 1。1 用户访问秒杀页面的时候，会返回一个字段，判断其有没有参与这个活动，没有参与则不能点击秒杀按钮
        // 1.2 获取当前用户
        String userId = UserInfoHandler.getUserId();
        // 获取锁
//        RLock lock = redissonClient.getLock(vo.getActId());
        ReentrantLock lock = new ReentrantLock(false);
        try {
            lock.lock();
            // 加锁
            // 因为会有这样查询数据库刷到Redis的操作，所以会有缓存击穿（大量请求访问数据库）的问题
            // 所以就使用Redisson的分布式锁
            // 如果缓存提前过期或者被误删除，这样就有了一层保险
            // 同样也有其他的问题，那便是 缓存击穿（缓存/数据库都没有数据）问题,使用布隆过滤器又会带来新的问题
            /*
             * 优点：不需要存储key，节省空间
             * 缺点：
             * 1. 算法判断key在集合中时，有一定的概率key其实不在集合中
             * 2. 无法删除
             */
//            boolean res =  lock.tryLock(waitTimeout,TimeUnit.SECONDS);
//            if (!res) {
//                throw new BusinessException("服务器繁忙，请重试");
//            }
            // 布隆过滤器绝大部分使用在缓存数据更新很少的场景中。
            // 所以就采取 将不存在的活动编号也缓存起来
            // 2.查询活动，判断是否还有库存
            String seckillAct = RedisEnums.SECKILL_ACTIVITY + vo.getActId();
            // 2.1 通过活动编号去Redis查询商品
            JSONObject json = Objects.isNull(redisUtil.get(seckillAct)) ? null : (JSONObject) redisUtil.get(seckillAct);
            SeckillActivityVo seckillActivityVo = Objects.isNull(json) ? null : json.toJavaObject(SeckillActivityVo.class);
            // 2.2 查询该活动
            if (Objects.isNull(seckillActivityVo)) {
                // 远程调用
                SeActivityDto seActivity = (SeActivityDto) activityFeign.getActivityInfo(vo.getActId()).getData();
//                SeActivityDto seActivity = activityJson.toJavaObject(SeActivityDto.class);
//                SeActivityDto seActivity = activityService.getOne(vo.getActId());
                if (Objects.isNull(seActivity)) {
                    // 2.3 如果数据库中也没有，就将这个数据缓存(带过期时间)，防止缓存穿透
                    // 这样的缓存归属与特殊的缓存，不该存在Redis中太久
                    SeckillActivityVo item = new SeckillActivityVo();
                    item.setActId(vo.getActId());
                    item.setSeActStock(0);
                    // TODO 感觉这里也需要设计过期时间
                    redisUtil.set(RedisEnums.SECKILL_ACTIVITY + vo.getActId(), JSONObject.toJSON(item));
                    redisUtil.set(RedisEnums.DECKILL_ACT_NUM + vo.getActId(), item.getSeActStock());
                    throw new BusinessException("不存在该活动");
                }
                seckillActivityVo = new SeckillActivityVo();
                // 2.4 如果数据库查到了，那就存入Redis中
                seckillActivityVo.setSeActStock(seActivity.getActNum());
                seckillActivityVo.setActId(seActivity.getId());
                seckillActivityVo.setStart(seActivity.getActStart());
                seckillActivityVo.setEnd(seActivity.getActEnd());
                seckillActivityVo.setPrice(seActivity.getActMoney());
                // 存入Redis
                redisUtil.set(
                        RedisEnums.SECKILL_ACTIVITY + vo.getActId(),
                        JSONObject.toJSON(seckillActivityVo),
                        seckillActivityVo.getEnd().getTime() - seckillActivityVo.getStart().getTime()
                );
                redisUtil.set(RedisEnums.DECKILL_ACT_NUM + vo.getActId(),
                        seActivity.getActNum(),
                        seckillActivityVo.getEnd().getTime() - seckillActivityVo.getStart().getTime()
                );
            }
            // 2.5 如果库存不足，则直接返回，秒杀失败
            if (seckillActivityVo.getSeActStock() <= 0) {
                throw new BusinessException("该商品已经抢完");
            }
            // 3.判断用户是否参与过秒杀
//            if (Objects.nonNull(redisUtil.get(RedisKeys.getSuccessUserInfo(RedisEnums.USER_JOIN_LIMIT + vo.getActId() + "_" + currentUser.getId())))) {
//                throw new BusinessException("该用户已经参与过该活动");
//            }
            /**
             * 如果在高并发下，有多个请求同时查询库存，当时都大于0。由于查询库存和更新库存非原则操作，则会出现库存为负数的情况，即库存超卖。
             */
            // 如何很好的解决库存的超卖，回库，库存不足，预扣库存----使用lua脚本
            // Lua脚本的bug特别可怕，由于Redis的单线程特点，一旦Lua脚本出现不会返回（不是返回值）得问题，那么这个脚本就会阻塞整个redis实例。
            // 所以一定测试好lua脚本是可通的

            //4. 库存问题
            // TODO 我这里使用StringBuilder应该不会有安全问题吧？外层套了一个Redisson分布式锁
            StringBuilder builder = new StringBuilder();
            // 4.1 判断是否存在该活动
            builder.append("if (redis.call('exists', KEYS[1]) == 1) then ");
            // 4.2 拿到库存
            builder.append("local stock = tonumber(redis.call('get',KEYS[1])); ");
            // 4.3 如果库存为 -1 就返回 1
            builder.append("if (stock <= 0) then ");
            builder.append("return 0; ");
            builder.append("end; ");
            // 4.4 如果还有库存就减一，并返回库存
            builder.append("if (stock > 0) then ");
            builder.append("redis.call('incrby',KEYS[1],-ARGV[1]) ");
            builder.append("return stock; ");
            builder.append("end; ");
            // 4.5 如果库存小于0 则返回 0
            builder.append("end; ");
            builder.append("return -1; ");
            // 4.6 执行lua脚本
            List<String> keys = new ArrayList<>();
            keys.add(RedisEnums.DECKILL_ACT_NUM + vo.getActId());
            DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
            defaultRedisScript.setScriptText(builder.toString());
            defaultRedisScript.setResultType(Long.class);
            /**
             * 状态码
             * -1：Redis中不存在
             * 实际值：购买前的数量
             * 0：库存不足
             */
            Long stock = -1L;// 默认失败
            try {
                stock = (Long) redisTemplate.execute(defaultRedisScript, keys, RedisEnums.BUY_NUM, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                throw new BusinessException("执行Lua脚本出现错误: " + e.getMessage());
            }
            // 证明预扣库存成功
            if (stock <= 0) {
                throw new BusinessException("库存不足");
            }
            // 秒杀成功
            // KEY 需要设置为 userId-ActId
            String successUser = RedisEnums.USER_JOIN_LIMIT + vo.getActId() + "_" + userId;
            String successUserInfo = RedisKeys.getSuccessUserInfo(successUser);
            // 不设置过期时间，在活动结束之后就直接移除
            redisUtil.set(successUserInfo, RedisEnums.BUY_NUM);
            OrderAddDto addDto = new OrderAddDto(vo.getActId(),userId, RedisEnums.BUY_NUM, seckillActivityVo.getPrice());

            rabbitTemplate.convertAndSend(mqProperties.getDefaultExchange(), mqProperties.getRouteKey(), JSONObject.toJSONString(addDto));
        } finally {
            // 解锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
