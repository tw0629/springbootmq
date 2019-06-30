package com.tian.rabbitmq.demo9_spring1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sender")
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //direct直连交换机模式
    public void send(String routingKey, String message) {
        rabbitTemplate.convertAndSend("direct", routingKey, message);           //第一个值是交换机名 第二个值是routingkey，第三个是内容
    }

}