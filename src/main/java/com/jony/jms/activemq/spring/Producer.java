package com.jony.jms.activemq.spring;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class Producer {
    //    指定使用某个resource
    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Resource(name = "testQueue")
    private Destination testQueue;


    @Resource(name = "testTopic")
    private Destination testTopic;

    //    队列发送消息
    public void sendMessage() {
        jmsTemplate.setDefaultDestination(testQueue);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("test-queue");
            }
        });
    }

    public void sendTopic(){
        jmsTemplate.setDefaultDestination(testTopic);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("test-topic");
            }
        });
    }

}
