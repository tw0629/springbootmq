package com.tian.rabbitmq.demo7_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicSend {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.83.128");
            factory.setPort(5672);
            factory.setPassword("admin");   //因为你添加 账户密码admin，所以必须写上, 而非默认
            factory.setUsername("admin");

            connection = factory.newConnection();
            channel = connection.createChannel();
//			声明一个匹配模式的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 待发送的消息
            String[] routingKeys = new String[]{"quick.orange.rabbit",
                    "lazy.orange.elephant",
                    "quick.orange.fox",
                    "lazy.brown.fox",
                    "quick.brown.fox",
                    "quick.orange.male.rabbit",
                    "lazy.orange.male.rabbit"};
//			发送消息
            for (String severity : routingKeys) {
                String message = "From " + severity + " routingKey' s message!";
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
                System.out.println("TopicSend [x] Sent '" + severity + "':'" + message + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}