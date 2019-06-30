package com.tian.kafka.demo5;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *  原文：https://blog.csdn.net/qq_31289187/article/details/80910775
 *
 *  Kafka分配Replica的算法如下：
 *
 * 1 将所有存活的N个Brokers和待分配的Partition排序
 *
 * 2 将第i个Partition分配到第(i mod n)个Broker上，这个Partition的第一个Replica存在于这个分配的Broker上，并且会作为partition的优先副本
 *
 * 3 将第i个Partition的第j个Replica分配到第((i + j) mod n)个Broker上
 *
 */
public class partitionAndBroker {

    /**
     * partition如何分布到不同的broker上
     */
    @Test
    public void kafkaProducter(){
        //partitions创建的分区，比如我创建了一个topic，
        // 设置的副本是1时，partitions = partition * 1;
        // 设置的副本为2时，partitions = partition * 2；
        List<String> partitions = new LinkedList<>();
        partitions.add("p0");
        partitions.add("p1");
        partitions.add("p2");
        partitions.add("p3");
        partitions.add("p0");//副本
        partitions.add("p1");//副本
        partitions.add("p2");//副本
        partitions.add("p3");//副本
        //borkers是kafka集群
        List<String> brokers = new LinkedList<>();
        brokers.add("b1");
        brokers.add("b2");
        brokers.add("b3");
        for(int i = 0;i<partitions.size();i++){
            System.out.println("分区"+partitions.get(i)+"在:"+brokers.get(i%brokers.size())+"的broker上");
            //System.out.println("分区"+partitions.get(i)+"在:"+brokers.get(i%brokers.size())+"的broker上");
            System.out.println("            ");
        }
    }


    /**
     * consumerGroup组员和partition之间如何做负载均衡
     */
    @Test
    public void kafkaConsumer(){
        List<String> partitions = new LinkedList<>();
        partitions.add("p0");
        partitions.add("p1");
        partitions.add("p2");
        partitions.add("p3");
        List<String> consumers = new LinkedList<>();
        consumers.add("c1");
        consumers.add("c2");
        consumers.add("c3");
        consumers.add("c4");
        consumers.add("c5");
        consumers.add("c6");
        //向上取整，计算每个消费者对应几个分区
        int m = (int) Math.ceil(partitions.size()*1.0/consumers.size());
        System.out.println("每个消费者对应几个分区 m:"+m);
        for (int i = 0;i<consumers.size();i++){
            System.out.print("消费者"+consumers.get(i)+"，对应的分区:");
            for(int j=0;j<m;j++){
                //如果下标大于等于partitions的元素个数，break
                if(i*m+j >= partitions.size()){
                    break;
                }
                System.out.println(partitions.get(i*m+j));
                System.out.println("            ");
            }
        }
    }


    /**
     *
     */

}
