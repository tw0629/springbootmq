package com.tian.activemq.demo4test.p2p;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage) message).getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

