package com.tian.activemq.demo4;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public class ActiveMQutil {

    private static ActiveMQutil activeMQutil = null;

    static {
        activeMQutil = new ActiveMQutil();
    }

    private ActiveMQutil() {
    }

    public static ActiveMQutil getActiveMQutil() {
        return activeMQutil;
    }


    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer messageProducer = null;
    private MessageConsumer messageConsumer = null;


    /**
     * 初始化链接工厂和链接
     *
     * @param userName
     * @param passWord
     * @param url
     */
    public void setConnectionFactory(String userName, String passWord, String url) {
        connectionFactory = new ActiveMQConnectionFactory(userName, passWord, url);
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息中间件发送消息方法
     *
     * @param queueName    队列名称
     * @param serializable 发送消息序列化内容
     */
    public void setProducerP2p(String queueName, Serializable serializable) {
        try {
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer, serializable);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

    }

    /**
     * 发送序列化的数据
     *
     * @param session      session
     * @param producer     消息生产者
     * @param serializable 发送消息序列化内容
     */
    public void sendMessage(Session session, MessageProducer producer, Serializable serializable) {
        try {
            ObjectMessage objectMessage = session.createObjectMessage(serializable);
            producer.send(objectMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    /**
     * 消息中间件接收消息方法
     *
     * @param queueName 队列名称
     */
    public void setCustomerP2p(String queueName) {
        try {
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(new ActiveMQListener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅模式发送消息方法封装
     *
     * @param queueName    订阅名称
     * @param serializable 发送订阅序列化消息
     */
    public void setProducerTop(String queueName, Serializable serializable) {
        try {
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(queueName);
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer, serializable);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

    }

    public void setCustomerTop(String queueName) {
        try {
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(queueName);
            messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(new ActiveMQListener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭链接
     *
     * @param connection 链接
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}