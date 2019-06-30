package com.tian.activemq.demo6;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 消息队列工具类
 * activemq/conf/activemq.xml文件中 broker节点下，修改managementContext节点内容为
 * <managementContext>
 * <managementContext createConnector="true" connectorPort="1093" connectorPath="/jmxrmi" jmxDomainName="org.apache.activemq"/>
 * </managementContext>
 * Created by tian.
 */


/**
 * ActiveMQ消息队列获取每个队列中的消费者数、剩余消息数、已消费数、队列名等信息
 *
 */

public class ActiveMQUtil {

    public static final String reportQueueName = "zc-queue-actual";//生成核对报告队列名
    private static Log log = LogFactory.getLog(ActiveMQUtil.class);
    private static final String connectorPort = "1093";
    private static final String connectorPath = "/jmxrmi";
    private static final String jmxDomain = "org.apache.activemq";


    public static Long getQueueSize(String queueName) {
        Map<String, Long> queueMap = getAllQueueSize();
        Long queueSize = 0L;
        if (queueMap.size() > 0) {
            queueSize = queueMap.get(queueName);
        }
        return queueSize;
    }


    public static Map<String, Long> getAllQueueSize() {
//    public static Map<String,Object> getAllQueueSize() {
        Map<String, Long> queueMap = new HashMap<String, Long>();
        BrokerViewMBean mBean = null;
        MBeanServerConnection connection = null;
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.83.128:" + connectorPort + connectorPath);
            JMXConnector connector = JMXConnectorFactory.connect(url);
            connector.connect();
            connection = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName(jmxDomain + ":brokerName=localhost,type=Broker");
            mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
        } catch (IOException e) {
            log.error("ActiveMQUtil.getAllQueueSize", e);
        } catch (MalformedObjectNameException e) {
            log.error("ActiveMQUtil.getAllQueueSize", e);
        }

        if (mBean != null) {
            for (ObjectName queueName : mBean.getQueues()) {
                QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
                queueMap.put(queueMBean.getName(), queueMBean.getQueueSize());
//                System.out.println("Queue Name --- " + queueMBean.getName());// 消息队列名称
//                System.out.println("Queue Size --- " + queueMBean.getQueueSize());// 队列中剩余的消息数
//                System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());// 消费者数
//                System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());// 出队数

            }
        }

        return queueMap;
    }

}