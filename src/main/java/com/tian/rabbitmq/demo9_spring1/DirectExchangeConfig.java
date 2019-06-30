package com.tian.rabbitmq.demo9_spring1;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//direct直连模式的交换机配置
@Configuration
public class DirectExchangeConfig {

    //直连交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    //添加两个队列
    @Bean
    public Queue queue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("queue2");
    }

    //三个binding将交换机和队列绑定起来
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(directExchange()).with("key1");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(directExchange()).with("key2");
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(queue2()).to(directExchange()).with("key3");
    }

}