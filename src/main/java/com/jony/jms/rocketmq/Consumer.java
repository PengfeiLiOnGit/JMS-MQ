package com.jony.jms.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {
    public void consumer() {
//        主动拉取消费者
//        DefaultMQPullConsumer defaultMQPullConsumer = new DefaultMQPullConsumer("group_consumer");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_consumer");
//        设置nameserver
        consumer.setNamesrvAddr("localhost:9876");
//        开启连接
        try {
//            订阅指定topic消息,第二个参数指的是次级标签
            consumer.subscribe("topic_java_test", "*");
//            设置获取信息顺序
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //        注解消费监听器
            consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                if (msgs != null) {
                    for (MessageExt msg : msgs) {
                        try {
                            System.out.printf(new String(msg.getBody(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
//                返回消费成功标识
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
//开启消费者
            consumer.start();

        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.consumer();
    }
}
