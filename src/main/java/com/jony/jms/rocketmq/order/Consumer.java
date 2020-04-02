package com.jony.jms.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Consumer {
    private String groupName;
    private String nameSrvAddr;
    private DefaultMQPushConsumer consumer;
    private String topicName;

    public Consumer(String groupName, String nameSrvAddr, String topicName) {
        this.groupName = groupName;
        this.nameSrvAddr = nameSrvAddr;
        this.topicName = topicName;
    }

    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameSrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

//         订阅消息
        consumer.subscribe(topicName,"*");
//        使用顺序（order） 消息监听器消费消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                if(msgs.size()>0){
                    for (MessageExt msg:msgs) {
                        try {
                            System.out.println(new String(msg.getBody(),"UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
    }

    public void destroy(){
        consumer.shutdown();
    }

    public DefaultMQPushConsumer getConsumer(){
        return consumer;
    }

}
