package com.jony.jms.rocketmq.spring;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {
    private String producerGroupName;
    private String nameServerAddr;
    private String topicName;
    private MessageListenerConcurrently messageListenerConcurrently;
    private DefaultMQPushConsumer consumer;

    public Consumer(String producerGroupName, String nameServerAddr, String topicName) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddr = nameServerAddr;
        this.topicName = topicName;
    }

    public void init() throws MQClientException {
//        创建消费者
        consumer = new DefaultMQPushConsumer(producerGroupName);
//        设置nameserver
        consumer.setNamesrvAddr(nameServerAddr);
//        订阅消息
        consumer.subscribe(topicName,"*");
        //        设置读取顺序
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        注册消息监听
        messageListenerConcurrently = (List<MessageExt> msgs, ConsumeConcurrentlyContext context)->{
            if(msgs!=null){
                for (MessageExt msg:msgs) {
                    try {
                        System.out.printf(new String(msg.getBody(),"UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        };
        consumer.registerMessageListener(messageListenerConcurrently);
//        启动
        consumer.start();
    }

    public void destory(){
        consumer.shutdown();
    }

    public DefaultMQPushConsumer getConsumer(){
        return consumer;
    }
}
