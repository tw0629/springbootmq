package com.tian.rabbitmq.demo8_optimize;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class BaseConnector {
    protected Channel channel;
    protected Connection connection;
    protected String queueName;

    public BaseConnector(String queueName) throws Exception {
        this.queueName = queueName;
        //打开连接和创建频道
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名  127.0.0.1即localhost
        factory.setHost("192.168.83.128");
        factory.setPort(5672);
        factory.setPassword("admin");   //因为你添加 账户密码admin，所以必须写上, 而非默认
        factory.setUsername("admin");


        //创建连接
        connection = factory.newConnection();
        //创建频道
        channel = connection.createChannel();
        //声明创建队列
        channel.queueDeclare(queueName, false, false, false, null);
    }
}