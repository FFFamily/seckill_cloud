//package com.tutu.netty;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 处理器
// * SimpleChannelInboundHandler UDP 单播
// */
//@Slf4j
//@EnableScheduling
//@Component
//public class NettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//    /**
//     * 用于记录和管理所有客户端的 channel
//     */
//    public static ChannelGroup clients =
//            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    @Autowired
//    private NettyServer nettyServerl;
//
////    @Scheduled(cron = "0/1 * * * * ?")
////    public void pushKline1() {
////        log.info("发送消息");
////        log.info("连接建立成功,当前连接数为：{}",clients.size());
////        clients.stream().forEach(item->item.writeAndFlush(new TextWebSocketFrame("1")));
////    }
//
////    @Scheduled(cron = "0/30 * * * * ?")
////    public void pushKline2() {
////        log.info("检测Netty服务器是否连接正常");
////        clients.stream().forEach(item->item.writeAndFlush(new TextWebSocketFrame("1")));
////    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
//        //获取客户端传来的信息
//        String str = msg.text();
//        log.info("传入的消息为：{}",str);
//        JSONObject entries = JSONUtil.parseObj(str);
//        String message = entries.getStr("Message");
//        if ("Hello".equals(message)){
//            clients.add(ctx.channel());
//            log.info("连接建立成功,当前连接数为：{}",clients.size());
//        } else {
//            ctx.channel().close();
//        }
//    }
//
//    /*
//     *客户端打开连接
//     * */
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        Channel channel =  ctx.channel();
//        List<Channel> collect = clients.stream().filter(item -> item == channel).collect(Collectors.toList());
//        if (collect.size() >0 ){
//            log.info("重复连接");
//            channel.close();
//        }
//    }
//
//    /**
//     * 客户端关闭
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
//        clients.remove(ctx.channel());
//    }
//
//    /**
//     * 客户端异常
//     * @param ctx
//     * @param cause
//     * @throws Exception
//     */
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
//        ctx.channel().close();
//        clients.remove(ctx.channel());
//        log.info(" netty出现异常： {}",cause.getMessage());
//    }
//
//    /**
//     * 心跳机制
//     * @param ctx
//     * @param evt
//     * @throws Exception
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        log.info("心跳");
//        // IdleStateEvent 当通道空闲时，由IdleStateHandler触发的用户事件。
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            String eventType = null;
//            // 获取超时事件：READER_IDLE、WRITER_IDLE或ALL_IDLE
//
//            log.info(ctx.channel().remoteAddress() + "超时事件：" + eventType);
//            ctx.channel().close();
//        }
//    }
//}
