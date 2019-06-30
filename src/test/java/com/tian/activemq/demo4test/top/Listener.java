package com.tian.activemq.demo4test.top;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String textMessage = ((TextMessage) message).getText();
            System.out.println("我来了 ===========>   " + textMessage);
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}