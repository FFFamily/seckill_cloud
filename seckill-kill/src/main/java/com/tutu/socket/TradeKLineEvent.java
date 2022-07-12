package com.tutu.socket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class TradeKLineEvent {

    @Autowired
    private CoinAPIWebsocketClient coinAPIWebsocketClient;

    /**
     * 创建/检测 WebSocket 连接
     */
    public synchronized void handle() {
        log.info("创建/检查 WebSocket 连接方法--开始");
        // 尝试开启CoinAPI的Websocket
        try {
            //serverUri
            // 判断当前状态，如果是Open状态则什么也不做
            log.info("当前 WebSocket 状态 ： {}", coinAPIWebsocketClient.getReadyState());
            if (coinAPIWebsocketClient != null && !coinAPIWebsocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                // 证明是关闭状态
                log.info("webSocket 未建立连接，正重新开始建立连接");
                // TODO 那么正在连接或者正在关闭等状态如何处理呢?
                if (coinAPIWebsocketClient.getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED)) {
                    try {
                        coinAPIWebsocketClient.connect();
                    } catch (IllegalStateException e) {
                    }
                } else if (coinAPIWebsocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSING) ||
                        coinAPIWebsocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSED)) {
                    coinAPIWebsocketClient.reconnect();
                }
                // 重新生成一个循环去take k线数据
                // 这个方法不清楚有什么作用，其中的queue中的数据从哪里来，没有看到push操作
                // send 方法的问题出现在这里
                // 如果还没有连接成功，就调用这个方法
                // 那么很有可能出现webSocket异常
                boolean flag = false;
                AtomicInteger atomicInteger = new AtomicInteger(0);
                while (coinAPIWebsocketClient != null && !flag && atomicInteger.getAndIncrement() != 10) {
                    log.info("等待 webSocket 建立连接,当前状态为 :{}", coinAPIWebsocketClient.getReadyState());
                    Thread.sleep(1000);
                    if (coinAPIWebsocketClient.isOpen()) {
                        // 建立连接发送消息
                        flag = coinAPIWebsocketClient.subscribeOHLCV();
                        atomicInteger.set(10);
                    } else if (coinAPIWebsocketClient.isClosing()) {
                        coinAPIWebsocketClient.onClose(-1, "手动关闭WebSocket", true);
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            log.info("webSocket 构建异常，出错原因可能是：TradeKLineEventURI构造失败/发送消息出现异常/webSocket 连接/重连异常 : {}", e.getMessage());
            e.printStackTrace();
        }
        log.info("创建/检查 WebSocket 连接方法--结束");
    }
}
