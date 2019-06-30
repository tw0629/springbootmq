package com.tian.activemq.demo3.发布者订阅者模型Demo;

import com.tian.activemq.demo3.点对点模型Demo.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MQCousumer02 {

    public static void main(String[] args) {
        // 连接工厂
        ConnectionFactory factory;
        // 连接实例
        Connection connection = null;
        // 收发的线程实例
        Session session;
        // 消息发送目标地址
        Destination destination;
        try {
            // 实例化连接工厂
            factory = new ActiveMQConnectionFactory(Constants.MQ_NAME, Constants.MQ_PASSWORD, Constants.MQ_BROKETURL);
            // 获取连接实例
            connection = factory.createConnection();
            // 启动连接
            connection.start();
            // 创建接收或发送的线程实例
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 创建队列（返回一个消息目的地）
            destination = session.createTopic("www");
            // 创建消息订阅者
            MessageConsumer consumer = session.createConsumer(destination);
            // 消息发布者添加监听器
            consumer.setMessageListener(new Listerner02());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}