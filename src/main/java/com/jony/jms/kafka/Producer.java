package com.jony.jms.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class Producer {
    public void produce() {
//        设置生产配置信息（key 信息）
        Map<String, Object> props = new HashMap<>();
//        消息服务器地址
        props.put("bootstrap.servers", "localhost:9092");
//        消息序列化与反序列化类的全限定名
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
//        zookeeper 集群地址
        props.put("zk.connect", "127.0.0.1:2181");

//        定义topic
        String topic = "test-topic";
//包装配置
        org.apache.kafka.clients.producer.Producer<String, Object> producer = new KafkaProducer<String, Object>(props);
//        发送消息
        producer.send(new ProducerRecord<String,Object>(topic,"idea-key","java-message 1"));

        producer.close();
    }
}
