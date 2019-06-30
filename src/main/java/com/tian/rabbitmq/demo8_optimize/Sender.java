package com.tian.rabbitmq.demo8_optimize;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

public class Sender extends BaseConnector {
    public Sender(String queueName) throws Exception {
        super(queueName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("", queueName, null, SerializationUtils.serialize(object));
    }
}