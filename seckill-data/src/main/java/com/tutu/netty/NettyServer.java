//package com.tutu.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 初始化 Netty
// */
//@Slf4j
//@Service("nettyServer")
//public class NettyServer {
//    // 设置服务端端口
//    private static final int port = 9876;
//    // 通过nio方式来接收连接和处理连接
//    private static EventLoopGroup boss = new NioEventLoopGroup();
//    // 通过nio方式来接收连接和处理连接
//    private static EventLoopGroup work = new NioEventLoopGroup();
//    // 服务
//    private static ServerBootstrap server = new ServerBootstrap();
//    @Autowired
//    private NettyServerFilter nettyServerFilter;
//
//    /**
//     *
//     */
//    public void run() {
//        try {
//            server.group(boss, work);
//            server.channel(NioServerSocketChannel.class);
//            // 设置过滤器
//            server.childHandler(nettyServerFilter);
//            // 服务器绑定端口监听
//            ChannelFuture future = server.bind(port).sync();
//            log.info("Netty服务-启动成功,端口是:" + port);
//            // 监听服务器关闭监听
//            future.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
//            work.shutdownGracefully();
//            boss.shutdownGracefully();
//        }
//    }
//
//}
