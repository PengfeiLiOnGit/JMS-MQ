package com.jony.jms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    public void consumer() {
        String topic = "test-topic";
//        设置配置属性
        Properties props = new Properties();
        //        消息服务器地址
        props.put("bootstrap.servers", "localhost:9092");
//        消费者组
        props.put("group.id","testGroup1");
//
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","1000");
//        消息序列化与反序列化类的全限定名
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
//        生成一个kafka 消费者实例
        org.apache.kafka.clients.consumer.Consumer consumer = new KafkaConsumer(props);
//        订阅/注册消息队列（可订阅多个）
        consumer.subscribe(Arrays.asList(topic));

//        拉取消息
        while (true){
//            每0.1秒拉取一次数据
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record:records) {
                String result = String.format("partition= %d,offset=%d,key=%s,value=%s%n",record.partition(),record.offset(),record.key(),record.value());
                System.out.println(result);
            }

        }



    }
}
