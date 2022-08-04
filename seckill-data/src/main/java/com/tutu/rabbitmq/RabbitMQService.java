package com.tutu.rabbitmq;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RabbitMQService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQService.class);
//    @Resource
//    private RedissonClient redissonClient;
//    @Resource
//    private RedisTemplate redisTemplate;
////    @Resource
//    private SeOrderMapper seOrderMapper;

//    @RabbitListener(queues = "${mq.queue}")
    @RabbitListener(queues = "queue")
    @RabbitHandler
//    @Transactional(rollbackFor = Exception.class)
//    public void receive(Object payload, Channel channel,
//                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
      public void receive(String str, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        System.out.println(str);
//        try {
//            Message msg = (Message) payload;
//            Object obj = SerializationUtils.deserialize(msg.getBody());
//            LOGGER.info("消费内容为：{}", obj);
//            if (obj instanceof ChanelOrderDto) {
//                ChanelOrderDto dto = (ChanelOrderDto) obj;
                // 异步处理
                // 锁键值,对这个活动加锁
//                String key = RedisEnums.ACTIVITY_ID + dto.getActId();
                // 获取锁
//                RLock lock = redissonClient.getLock(key);
//                try {
//                    lock.lock();
//                    StringBuilder builder = new StringBuilder();
//                    // 4.1 判断是否存在该活动
//                    builder.append("if (redis.call('exists', KEYS[1]) == 1) then ");
//                    builder.append("redis.call('incrby',KEYS[1],ARGV[1]) ");
//                    builder.append("return 1; ");
//                    builder.append("end; ");
//                    builder.append("return -1; ");
//                    // 4.6 执行lua脚本
//                    List<String> keys = new ArrayList<>();
//                    keys.add(RedisEnums.DECKILL_ACT_NUM + dto.getActId());
//                    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
//                    defaultRedisScript.setScriptText(builder.toString());
//                    defaultRedisScript.setResultType(Long.class);
//                    Long stock = -1L;// 默认失败
//                    try {
//                        stock = (Long) redisTemplate.execute(defaultRedisScript, keys, dto.getBugNum(), TimeUnit.MILLISECONDS);
//                    } catch (Exception e) {
//                        throw new BusinessException("执行Lua脚本出现错误: " + e.getMessage());
//                    }
//                    if (stock != 1L) {
//                        throw new BusinessException("未发现对应的缓存");
//                    }
//                } catch (Exception e) {
//                    LOGGER.warn("异步处理消息出错:{}", e.getMessage());
//                } finally {
//                    lock.unlock();
//                }
//            } else if (obj instanceof OrderAddDto) {
//                OrderAddDto orderAddDto = (OrderAddDto) obj;
//                SeOrder seOrder = new SeOrder();
//                seOrder.setId(IdWorkerUtil.nextId());
//                seOrder.setOrdNo(IdWorkerUtil.nextId());
//                seOrder.setPayNum(orderAddDto.getBuyNum());
//                seOrder.setPayMoney(orderAddDto.getPayMoney());
//                seOrder.setActId(orderAddDto.getActId());
//                seOrder.setUserId(orderAddDto.getId());
//                seOrder.setState(Integer.parseInt(Constants.YES));
//                seOrder.setTime(DateUtil.getDate());
//                seOrderMapper.insert(seOrder);
//            }
//        }catch (Exception e){
//            channel.basicAck(tag, false);
            // 有异常拒收消息
//            log.info("============消息处理异常，拒收消息===============");
//            channel.basicNack(tag, false, true);
//            throw new BusinessException("消息处理异常");
//        }
//        log.info("============消息处理完成，准备确认===================");
//        channel.basicAck(tag, false);
    }
}
