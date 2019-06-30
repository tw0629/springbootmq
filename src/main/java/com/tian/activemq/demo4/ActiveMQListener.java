package com.tian.activemq.demo4;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


public class ActiveMQListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((ObjectMessage) message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}