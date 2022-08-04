package com.tutu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients//服务调用
@EnableDiscoveryClient // 服务注册
public class DataApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DataApplication.class, args);
//        NettyServer nettyServer = context.getBean(NettyServer.class);
//        nettyServer.run();
    }
}