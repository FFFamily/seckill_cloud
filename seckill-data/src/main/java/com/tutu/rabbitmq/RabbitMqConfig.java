//package com.tutu.rabbitmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableRabbit
//@Slf4j
//public class RabbitMqConfig {
//    @Autowired
//    private MqProperties mqProperties;
//
//    @Bean
//    public Queue queue() {
//        boolean durable = true;
//        boolean exclusive = false;
//        boolean autoDelete = false;
//        log.info("MQ启动-生成队列");
//        return new Queue(mqProperties.getQueue(), durable, exclusive, autoDelete);
//    }
//
//    @Bean
//    public DirectExchange defaultExchange() {
//        boolean durable = true;
//        boolean autoDelete = false;
//        log.info("MQ启动-生成交换机");
//        return new DirectExchange(mqProperties.getDefaultExchange(), durable, autoDelete);
//    }
//
//    @Bean
//    public Binding binding() {
//        log.info("MQ启动成功");
//        return BindingBuilder.bind(queue())
//                .to(defaultExchange())
//                .with(mqProperties.getRouteKey());
//    }
//}
