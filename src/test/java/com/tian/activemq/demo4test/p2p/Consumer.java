package com.tian.activemq.demo4test.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {


    private static final String userName = ActiveMQConnection.DEFAULT_USER;
    private static final String passWord = ActiveMQConnection.DEFAULT_PASSWORD;
    //    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String url = "tcp://192.168.83.128:61616";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(Consumer.userName, Consumer.passWord, Consumer.url);
            connection = connectionFactory.createConnection();
            connection.start();//启动连接
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FiretQuery1");
            messageConsumer = session.createConsumer(destination);
			/*while(true){
				TextMessage message = (TextMessage) messageConsumer.receive(1000);
				if(message != null){
					System.out.println(message);
				}else{
					break;
				}
			}*/
            messageConsumer.setMessageListener(new Listener());

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}