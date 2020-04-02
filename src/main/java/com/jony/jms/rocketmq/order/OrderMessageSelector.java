package com.jony.jms.rocketmq.order;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderMessageSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        Integer id = (Integer) arg;
//        返回queue，对信息进行归类在不同的队列中，相同id的消息丢到相同的队列
        return mqs.get(id % mqs.size());
    }
}
