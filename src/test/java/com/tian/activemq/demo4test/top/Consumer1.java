package com.tian.activemq.demo4test.top;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer1 {

    private static final String userName = ActiveMQConnection.DEFAULT_USER;
    private static final String passWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;

        connectionFactory = new ActiveMQConnectionFactory(Consumer1.userName, Consumer1.passWord, Consumer1.url);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("TOP3");
            consumer = (MessageConsumer) session.createConsumer(destination);
            consumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}