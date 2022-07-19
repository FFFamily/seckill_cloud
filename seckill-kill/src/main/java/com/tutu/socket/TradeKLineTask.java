package com.tutu.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TradeKLineTask {

    @Autowired
    private TradeKLineEvent tradeKLineEvent;

    /**
     * 币币交易生成一次K线,每1小时处理一次，防止掉线
     * initialDelay： 首次执行fixedRate或fixedDelay任务之前的延迟毫秒数。
     * fixedRate： 在调用之间以固定的毫秒周期执行带注释的方法。
     */
//    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 3)
    public void generateKLine() {
        log.info("定时任务--每1分钟检测连接 webSocket--开始");
        tradeKLineEvent.handle();
        log.info("定时任务--每1分钟检测连接 webSocket--结束");
    }

}