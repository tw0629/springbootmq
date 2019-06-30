package com.tian.activemq.demo4test.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Produce {

    private static final String UserName = ActiveMQConnection.DEFAULT_USER;
    private static final String PassWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";
    private static final Integer num = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;//创建连接工厂
        Connection connection = null;//创建连接
        Session session = null;//创建session
        Destination destination = null;//创建目的地
        MessageProducer messageProducer = null;//创建消息发送者

        try {
            connectionFactory = new ActiveMQConnectionFactory(Produce.UserName, Produce.PassWord, Produce.url);//实例化连接工厂
            connection = connectionFactory.createConnection();//连接工厂实例化连接
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);//连接实例化session
            destination = session.createQueue("FiretQuery2");//session实例化目的地
            messageProducer = session.createProducer(destination);//session实例化消息发送者
            sendMessage(session, messageProducer);
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

    public static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < Produce.num; i++) {
//        for(int i=0; i<1000000000; i++){
            TextMessage message = session.createTextMessage("点对点发送消息" + i);
            System.err.println("消息发送：" + "点对点发送消息" + i);
            messageProducer.send(message);
        }
    }


}