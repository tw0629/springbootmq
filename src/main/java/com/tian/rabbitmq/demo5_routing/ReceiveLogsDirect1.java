package com.tian.rabbitmq.demo5_routing;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsDirect1 {
    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info", "warning", "error"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.83.128");
        factory.setPort(5672);
        factory.setPassword("admin");   //因为你添加 账户密码admin，所以必须写上, 而非默认
        factory.setUsername("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


//		声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");       //todo


//		获取匿名队列名称
        String queueName = channel.queueDeclare().getQueue();
//		根据路由关键字进行多重绑定
        for (String severity : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
            System.out.println("ReceiveLogsDirect1 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", BindRoutingKey:" + severity);
        }
        System.out.println("ReceiveLogsDirect1 [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}