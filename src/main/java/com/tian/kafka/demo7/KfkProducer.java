package com.tian.kafka.demo7;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KfkProducer {
    private static Properties properties;

    static{
        properties = new Properties();
        //服务器端口
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "你的kafkaip:端口号");
        //发送策略 分3中 ”all“，1，0
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        //如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //序例化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    }

    //这里 的处理逻辑不是很好，凑合着看
    public void sendTos(String value){
        Producer<String, String> procuder = new KafkaProducer<String, String>(properties);
        ProducerRecord<String,String> record = new ProducerRecord<String, String>("test-yl","1",value);
        //这里也可以不要后面个回掉函数，看你选择，我是测试用
        procuder.send(record,new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
                System.out.println("消息传输成功 主题："+metadata.topic()+ "  分区："+metadata.partition()+"  偏移量："+metadata.offset());
            }
        });

    }

}