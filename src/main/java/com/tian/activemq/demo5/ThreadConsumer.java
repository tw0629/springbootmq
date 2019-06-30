package com.tian.activemq.demo5;

import org.apache.activemq.ActiveMQConnection;

public class ThreadConsumer {

    private static final String userName = ActiveMQConnection.DEFAULT_USER;
    private static final String passWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";

    public static void main(String[] args) {
        ThreadConsumer t = new ThreadConsumer();

        new Thread(t.new ThreadCon(), "1t").start();
        new Thread(t.new ThreadCon(), "2t").start();
        new Thread(t.new ThreadCon(), "3t").start();
    }

    private class ThreadCon implements Runnable {
        public void run() {
            ThreadActiveMqUtil threadActiveMqUtil = ThreadActiveMqUtil.getThreadActiveMqUtil();
            threadActiveMqUtil.init(userName, passWord, url);
            threadActiveMqUtil.p2pComsumer("通道1");
        }

    }
}