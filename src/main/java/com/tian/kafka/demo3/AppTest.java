package com.tian.kafka.demo3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://github.com/FSixteen/kafkautils
 */
public class AppTest extends TestCase {

    /**
     * kafka-topics.sh --create --zookeeper 172.16.42.5:2181 --replication-factor
     * 2 --partitions 12 --topic car </br>
     * kafka-topics.sh --delete --zookeeper 172.16.42.5:2181 --replication-factor
     * 2 --partitions 12 --topic car </br>
     */

    /**
     * 测试.
     *
     * @param args
     *          Formal Parameter
     */
    public static void main(String[] args) {
        boolean s = false;
        if (s) {
            //KafkaProducerUtils u = new KafkaProducerUtils("192.168.56.2:9092,192.168.56.3:9092,192.168.56.4:9092");
            KafkaProducerUtils u = new KafkaProducerUtils("192.168.83.128:9092");
            List<String> ls = new ArrayList<String>();
            long l = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                ls.add(String.valueOf(i));
            }
            System.out.println("发送的list为"+ls.toString());
            //一起发送一个list集合
            u.sendListMessage("test", ls);

            long m = System.currentTimeMillis();
            System.out.println("发送耗时=========>"+(m - l)+"ms"); // 551
        } else {
            //KafkaProducerUtils u = new KafkaProducerUtils("192.168.56.2:9092,192.168.56.3:9092,192.168.56.4:9092");
            KafkaProducerUtils u = new KafkaProducerUtils("192.168.83.128:9092");
            long l = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                System.out.println("本次发送的i为"+i);
                //一个一个的发送
                u.sendOneMessage("test", String.valueOf(i));
            }
            long m = System.currentTimeMillis();
            System.out.println("发送耗时=========>"+(m - l)+"ms"); // 55586
        }
    }

    /**
     * Create the test case.
     *
     * @param testName
     *          name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}