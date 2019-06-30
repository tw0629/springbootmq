package com.tian.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class test {

    @Test
    public void test() {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("tian");
            MessageConsumer consumer = session.createConsumer(destination);


            Message message = consumer.receive(1000000);
            TextMessage textMessage = (TextMessage) message;


        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
