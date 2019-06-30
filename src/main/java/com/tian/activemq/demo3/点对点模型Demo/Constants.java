package com.tian.activemq.demo3.点对点模型Demo;

import org.apache.activemq.ActiveMQConnection;

public class Constants {

//    public static final String MQ_NAME = "admin";
//    public static final String MQ_PASSWORD = "admin";

    public static final String MQ_NAME = ActiveMQConnection.DEFAULT_USER;
    public static final String MQ_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static final String MQ_BROKETURL = "tcp://192.168.83.128:61616";
}
