package com.tian.activemq.demo4test.top;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static java.lang.Thread.sleep;

public class TopProduce {

    private static final String userName = ActiveMQConnection.DEFAULT_USER;
    private static final String passWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";
    private static final Integer num = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;

        connectionFactory = new ActiveMQConnectionFactory(TopProduce.userName, TopProduce.passWord, TopProduce.url);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("TOP2");
            producer = (MessageProducer) session.createProducer(destination);
            sendMessage(session, producer);
            session.commit();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void sendMessage(Session session, MessageProducer producer) {
        for (int i = 0; i < num; i++) {
//        for(int i=0; i<100000; i++){
            try {
//                Thread.sleep(1000);
                TextMessage message = session.createTextMessage("订阅模式发送消息" + i);
                System.out.println("订阅模式：" + "订阅模式发送消息" + i);
                producer.send(message);
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }

}