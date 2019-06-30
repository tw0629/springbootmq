package com.tian.activemq.demo3.发布者订阅者模型Demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listerner02 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("订阅者02接收到消息：" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}