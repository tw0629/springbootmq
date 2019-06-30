package com.tian.activemq.demo6test;

import com.tian.activemq.demo6.ActiveMQUtil;
import org.junit.Test;

import java.util.Map;


/**
 * ActiveMQ消息队列获取每个队列中的消费者数、剩余消息数、已消费数、队列名等信息
 */

public class test {

    @Test
    public void test() {
        Long parryQuene2 = ActiveMQUtil.getQueueSize("parryQuene2");
        System.out.println("parryQuene2 size ===========> " + parryQuene2);

        Map<String, Long> allQueueSize = ActiveMQUtil.getAllQueueSize();
        System.out.println("192.168.83.128 activemq allQueueSize  ===========> " + allQueueSize);
//        System.out.println("Queue Name --- " + allQueueSize.getName());// 消息队列名称
//        System.out.println("Queue Size --- " + allQueueSize.getQueueSize());// 队列中剩余的消息数
//        System.out.println("Number of Consumers --- " + allQueueSize.getConsumerCount());// 消费者数
//        System.out.println("Number of Dequeue ---" + allQueueSize.getDequeueCount());// 出队数
    }
}
