package com.tian.kafka.demo7;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class StreamDemo {

    public static void init(){

        Properties config = new Properties();
        //这个配置 我也不是很清楚，只知道会生成两个这个topic
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "teset");
        //连接的端口号
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.83.128:9092");
        //序列化和反序列化
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> textLines;
        //第一个参数是 从哪里获取源流
        //第二个参数是 如何进行序列化
        //后面的.filter()方法是 过滤方法，过滤一些你不需要的 数据 我这里是过滤了 一下资源文件，写法很不好，重点是比较直观
        textLines = builder.stream("test002" , Consumed.with(Serdes.String(), Serdes.String())).filter(new Predicate<Object, Object>() {
            @Override
            public boolean test(Object s, Object s2) {
                //LogBean log = (LogBean)JSON.parse(s2.toString());
                if (s2.toString().contains(".js")){
                    return false;
                }else if(s2.toString().contains(".css")){
                    return false;
                }else if(s2.toString().contains(".png")){
                    return false;
                }else if(s2.toString().contains(".gif")){
                    return false;
                }else if(s2.toString().contains(".jpg")){
                    return false;
                }else if(s2.toString().contains(".jpeg")){
                    return false;
                }
                return true;
            }
        });


        textLines
//              .mapValues(new MyMapValues())  这里是一个有状态的改变，对每条数据都进行一个有状态的改变

                //.flatMapValues(Line -> Arrays.asList(Line.toLowerCase().split(" ")))
                .groupBy((key, word) -> word) //分组 按value 分组
                .count()  //聚合方法
                .toStream() //转换成KStream对象
                //.process(MyProcessor::new); //处理器
                .process(MyProcessor::new)
                .to("test004", Produced.with(Serdes.String(), Serdes.Long())); //接入到 test004的这个topic


        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start(); //启动
    }
}
