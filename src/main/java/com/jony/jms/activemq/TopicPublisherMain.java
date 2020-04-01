package com.jony.jms.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicPublisherMain {
    //    默认用户名
    public static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;

    //    默认密码
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;


    public void send(){
//        创建工厂，创建连接、启动连接、创建topic、创建会话、发送
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("topic-test");

            MessageProducer publisher = session.createProducer(topic);
            TextMessage message = session.createTextMessage("test");
            publisher.send(message);

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
