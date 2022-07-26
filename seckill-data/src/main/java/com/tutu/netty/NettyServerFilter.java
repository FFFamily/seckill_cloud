package com.tutu.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 管道初始化
 */
@Slf4j
@Component
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("管道初始化-开始");
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 增加心跳支持 start
        // 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        // readerIdleTime 指定时间没有读，进入读空闲
        // writerIdleTime 指定时间没有写，进入写空闲
        // allIdleTime 指定时间没有读写炒作，进入读写空闲阶段
        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        /**
         * 传输的协议 Protobuf
         * ph.addLast(new ProtobufVarint32FrameDecoder());
         * ph.addLast(new ProtobufDecoder(UserMsg.getDefaultInstance()));
         * ph.addLast(new ProtobufVarint32LengthFieldPrepender());
         * ph.addLast(new ProtobufEncoder());
         */
        // 传入协议 Http
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此hanler
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        // 以下是支持httpWebsocket
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //业务逻辑实现类
        pipeline.addLast(new NettyServerHandler());
    }
}
