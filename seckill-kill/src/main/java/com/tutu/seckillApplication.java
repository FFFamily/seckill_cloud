package com.tutu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@RefreshScope 下一次方法调用会获得一个新的实例
//@EnableScheduling // 开启定时器
@SpringBootApplication
@EnableFeignClients//服务调用
@EnableDiscoveryClient // 服务注册
public class seckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(seckillApplication.class, args);
    }

}
