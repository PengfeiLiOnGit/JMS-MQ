package com.jony.jms.rocketmq.spring;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class Producer {

    private String producerGroupName;
    private String nameServerAddr;
    private DefaultMQProducer producer;

    private Producer() {

    }

    public Producer(String producerGroupName, String nameServerAddr) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddr = nameServerAddr;
    }

    public void init(){
//        创建生产者
        producer = new DefaultMQProducer(producerGroupName);
//        绑定nameserv
        producer.setNamesrvAddr(nameServerAddr);
//        初始化start
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void destory(){
//        销毁producer
        producer.shutdown();
    }

    public DefaultMQProducer getProducer(){
        return producer;
    }
}
