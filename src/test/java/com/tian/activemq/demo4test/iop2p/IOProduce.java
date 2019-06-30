package com.tian.activemq.demo4test.iop2p;

import com.tian.activemq.demo4.ActiveMQutil;
import org.apache.activemq.ActiveMQConnection;

public class IOProduce {

    private static final String userName = ActiveMQConnection.DEFAULT_USER;
    private static final String passWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";

    public static void main(String[] rge) {
        ActiveMQutil activeMQutil = ActiveMQutil.getActiveMQutil();
        activeMQutil.setConnectionFactory(userName, passWord, url);
        activeMQutil.setProducerP2p("测试", "测试序列化");
    }
}