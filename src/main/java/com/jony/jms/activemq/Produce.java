package com.jony.jms.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者
 */
public class Produce {
    //    默认用户名
    public static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;

    //    默认密码
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static void main(String[] args) {
        Produce produce = new Produce();
        produce.send();
    }
    public void send() {
        try {
            //       连接工厂、连接、会话、目的地(队列或主题)、创建生产者、消息、发送
//        ConnectionFactory factory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,BROKER_URL);
            ConnectionFactory factory = new ActiveMQConnectionFactory();
            Connection connection = factory.createConnection();
//            启动连接
            connection.start();
//            创建会话，指定话会的事物性，与通知策略
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//            创建目的地队列
            Queue queue = session.createQueue("test-queue");
//创建消息生产者- 发送消息到目的地
            MessageProducer producer = session.createProducer(queue);
            Message message = session.createTextMessage("this is a queue test");
//            设置消息持久化
            message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
//            message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

            //
            producer.send(message);
//            发送消息，设置消息持久化，优先级，过期时限
            //            producer.send(message,DeliveryMode.PERSISTENT,10,1000);
            //提交事物
            session.commit();
//            session.rollback();

            //            提交事物与关闭会话与连接
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

