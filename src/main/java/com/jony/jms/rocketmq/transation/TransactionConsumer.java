package com.jony.jms.rocketmq.transation;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TransactionConsumer {
    /**
     * 消费者声明
     * 设置读取顺序
     * 设置服务器地址
     * 订阅topic
     * 监听消息
     */
    public void consumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_consumer_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTransaction","*");

        consumer.registerMessageListener((List< MessageExt > msgs, ConsumeConcurrentlyContext context)->{
            if (msgs.size()>0){
                for (MessageExt msg:msgs) {
//                    消费消息
                    try {
                        System.out.println(new String(msg.getBody(),"UTF-8"));
                        System.out.println(msg.getKeys());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
//                        如果事务处理失败，则应答稍后消费，超过6次后进入死信队列
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }

    public static void main(String[] args) {
        TransactionConsumer consumer = new TransactionConsumer();
        try {
            consumer.consumer();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
