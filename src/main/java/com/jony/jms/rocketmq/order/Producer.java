package com.jony.jms.rocketmq.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class Producer {
    private String groupName;
    private String nameSrvAddr;
    private DefaultMQProducer producer;

    public Producer(String groupName, String nameSrvAddr) {
        this.groupName = groupName;
        this.nameSrvAddr = nameSrvAddr;
    }

    /**
     * 初始化
     */
    public void init() throws MQClientException {
//        声明生产者
        producer = new DefaultMQProducer(groupName);
//        服务地地址
        producer.setNamesrvAddr(nameSrvAddr);
//
        producer.start();
    }

    public void destroy(){
        producer.shutdown();
    }

    public DefaultMQProducer getProducer(){
        return producer;
    }
}
