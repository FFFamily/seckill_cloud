package com.tutu.socket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class CoinAPIWebsocketClient extends WebSocketClient {
    // 空闲线程会呆多60秒，感觉没啥用，所以还是得自定义实现，1秒就销掉它。
    public static ExecutorService pool = new ThreadPoolExecutor(20, Integer.MAX_VALUE,
            1L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());
    private static HashMap<String, String> symbolMap;
    // 非公平锁
    private static ReentrantLock lock = new ReentrantLock();
    private static ThreadLocal<DateFormat> timeThreadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
        }
    };
    private static URI serverUri;

    static {
        try {
            serverUri = new URI("wss://ws.coinapi.io/v1/");
            symbolMap = new HashMap<String, String>();
            symbolMap.put("COINBASE_SPOT_BTC_USDT", "btcusdt");
            symbolMap.put("COINBASE_SPOT_ETH_USDT", "ethusdt");
            symbolMap.put("COINBASE_SPOT_ETH_BTC", "ethbtc");
            symbolMap.put("COINBASE_SPOT_BTC_USD", "btcusd");
            symbolMap.put("COINBASE_SPOT_ETH_USD", "ethusd");
            symbolMap.put("COINBASE_SPOT_USDT_USD", "usdtusd");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public CoinAPIWebsocketClient() {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("请求状态：{}", handshakedata.getHttpStatus());
        log.info("请求状态信息：{}", handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
        try {
            JSONObject obj = (JSONObject) JSONObject.parse(message);
            log.info("消息处理 onMessage 方法--开始 : {}", obj);
            CreateKLineDto createKLineDto = new CreateKLineDto();
            String dateStr = obj.getString("time_period_start");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
            // 改用 threadlocal 处理 SimpleDateFormat 的并发问题
            DateFormat dateFormat = timeThreadLocal.get();
            dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT-8"));
            Date d = null;
            try {
                d = dateFormat.parse(dateStr);
                log.info("拿到的时间为 : {}", d.getTime());
            } catch (ParseException e) {
                return;
            }
            // 非空判断
            createKLineDto.setDate(d == null ? null : new Long(d.getTime()));
            log.info("时间戳，{}", createKLineDto.getDate());
            String symbol = symbolMap.get(obj.getString("symbol_id"));
            createKLineDto.setSymbol(symbol);
            createKLineDto.setPrice(new BigDecimal(obj.getString("price_close")));
            createKLineDto.setVolume(new BigDecimal(obj.getString("volume_traded")));
            createKLineDto.setOpen(new BigDecimal(obj.getString("price_open")));
            createKLineDto.setClose(new BigDecimal(obj.getString("price_close")));
            createKLineDto.setHigh(new BigDecimal(obj.getString("price_high")));
            createKLineDto.setLow(new BigDecimal(obj.getString("price_low")));
            // 异步处理
            pool.execute(new GenerateKLineTask(createKLineDto));
            log.info("消息处理 onMessage 方法--结束");
        } catch (Exception e) {
            log.info("onMessage 方法异常: {}", e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("code : {}", code);
        log.info("reason : {}", reason);
        log.info("remote : {}", remote);
    }

    @Override
    public void onError(Exception ex) {
        log.info("连接出现异常：{}", ex.getMessage());
        ex.printStackTrace();
        log.info("准备重新建立 webSocket 连接");
    }


    /**
     * 发送初始验证信息
     *
     * @return 返回的是方法是否执行完成
     * @throws InterruptedException
     */
    public boolean subscribeOHLCV() throws InterruptedException {
        try {
            log.info("subscribeOHLCV 方法");
            JSONObject params = new JSONObject();
            //交易对
            String symbols[] = new String[6];
            symbols[0] = "COINBASE_SPOT_BTC_USDT$";
            symbols[1] = "COINBASE_SPOT_ETH_USDT$";
            symbols[2] = "COINBASE_SPOT_ETH_BTC";
            symbols[3] = "COINBASE_SPOT_BTC_USD$";
            symbols[4] = "COINBASE_SPOT_ETH_USD$";
            symbols[5] = "COINBASE_SPOT_USDT_USD$";
            String[] types = new String[1];
            types[0] = "ohlcv";
            String[] periods = new String[1];
            periods[0] = "1MIN";
            params.put("type", "hello");
            params.put("apikey", "D9342E00-B264-4F3D-B40C-64FBA145C200");
            params.put("heartbeat", false);
            params.put("subscribe_data_type", types);
            params.put("subscribe_filter_symbol_id", symbols);
//     params.put("subscribe_update_limit_ms_quote",1000);//订阅更新的毫秒报价，暂时没有用
            params.put("subscribe_filter_period_id", periods);
            String jsonStr = JSONObject.toJSONString(params);
            log.info("发送的消息为：{}", jsonStr);
            this.send(jsonStr);
        } catch (Exception e) {
            log.info("发送初始消息出现异常，异常信息为：{}", e.getMessage());
            return false;
        }
        return true;
    }
}
