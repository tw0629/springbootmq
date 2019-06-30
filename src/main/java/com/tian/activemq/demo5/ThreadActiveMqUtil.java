package com.tian.activemq.demo5;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activeMQ多线程并发 p2p 简单封装
 */
public class ThreadActiveMqUtil {

    private static final ThreadActiveMqUtil threadActiveMqUtil = new ThreadActiveMqUtil();

    private ThreadActiveMqUtil() {

    }

    public static ThreadActiveMqUtil getThreadActiveMqUtil() {
        return threadActiveMqUtil;
    }

    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Queue queue = null;


    ThreadLocal<MessageProducer> tlpro = new ThreadLocal<MessageProducer>();
    ThreadLocal<MessageConsumer> tlcon = new ThreadLocal<MessageConsumer>();
    ThreadLocal<Integer> tlCount = new ThreadLocal<Integer>();

    /**
     * 实例化链接通道
     *
     * @param userName
     * @param passWord
     * @param url
     */
    public void init(String userName, String passWord, String url) {
        connectionFactory = new ActiveMQConnectionFactory(userName, passWord, url);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param name 队列名字
     */
    public void p2pProduce(String name) {
        try {
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            queue = session.createQueue(name);
            MessageProducer producer = tlpro.get();
            Integer num = tlCount.get();
            if (producer == null) {
                producer = session.createProducer(queue);
                tlpro.set(producer);
            }
            if (num == null) {
                num = 0;
                tlCount.set(num);
            }

            while (true) {
                TextMessage message = session.createTextMessage(Thread.currentThread().getName() + num + ":发送信息-");
                System.out.println(Thread.currentThread().getName() + "-" + num + ":发送信息-");
                producer.send(message);
                session.commit();
                num++;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接收消息
     *
     * @param name
     */
    public void p2pComsumer(String name) {
        try {
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(name);
            MessageConsumer consumer = tlcon.get();
            Integer num = tlCount.get();
            if (consumer == null) {
                consumer = session.createConsumer(queue);
                tlcon.set(consumer);
            }
            if (num == null) {
                num = 0;
                tlCount.set(num);
            }

            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                if (message != null) {
                    message.acknowledge();//接收到消息，返回一个应答
                    System.out.println(Thread.currentThread().getName() + "-" + num + "-接收消息:" + message);
                    Thread.sleep(500);
                    num++;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭链接
     *
     * @param con
     */
    public void closeCon(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}