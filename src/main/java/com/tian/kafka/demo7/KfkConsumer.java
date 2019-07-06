package com.tian.kafka.demo7;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class KfkConsumer {

    private static Properties properties;
    private KafkaConsumer<String, String> kafkaConsumer;
    private String topic;
    private String group;

    public KfkConsumer(String group,String topic){
        this.group = group;
        this.topic=topic;
        intt();
    }

    public void intt(){
        properties = new Properties();
        //连接的服务器
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "你的kafkaip:端口号");
        //消费组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        //是否自动提交数据
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //自动提交的时间间隔
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //关于重新读取策略
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //心跳时间
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //序列化和反序列
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");
//        System.setProperty("java.security.auth.login.config", ".../kafka_client_jaas.conf"); // 环境变量添加，需要输入配置文件的路径
//        properties.put("security.protocol", "SASL_PLAINTEXT");
//        properties.put("sasl.mechanism", "PLAIN");

    }
    //发送消费信息的方法
    public List Monitor(){
        kafkaConsumer= new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topic));//订阅的topic 可以写多个
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            records.forEach(record->{
                System.out.printf("topic: %s , partition: %d , offset = %d, key = %s, value = %s%n", record.topic(),
                        record.partition(), record.offset(), record.key(), record.value());
            });
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.printf("topic: %s , partition: %d , offset = %d, key = %s, value = %s%n", record.topic(),
//                            record.partition(), record.offset(), record.key(), record.value());
//
//                }
        }
    }
}