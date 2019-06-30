package com.tian.rabbitmq.demo4_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue2";

    public static void main(String[] argv) throws java.io.IOException, Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.83.128");
        factory.setPort(5672);
        factory.setPassword("admin");   //因为你添加 账户密码admin，所以必须写上, 而非默认
        factory.setUsername("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);  //todo

//		·Ö·¢ÏûÏ¢
        for (int i = 0; i < 1000000000; i++) {
            String message = "Hello World! " + i;
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}