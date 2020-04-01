package com.jony.jms.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    //    默认用户名
    public static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;

    //    默认密码
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.consumer();
    }

    public void consumer() {
//        工厂、连接、开启连接、目的地队列、消费者、监听消息
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        try {
            Connection connection = factory.createConnection();
            connection.start();

//            设置会话，设置是否使用事物，在此会话中根据commit 与 rollback 进行事物处理
            final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("test-queue");
            MessageConsumer consumer = session.createConsumer(queue);

//            手动调用receive 方法接收消息
            //            Message message = consumer.receive();
// 监听消息
            consumer.setMessageListener(new MessageListener() {
                //                监听消息
                public void onMessage(Message msg) {
                    TextMessage message = (TextMessage) msg;
                    try {
                        System.out.println(message.getText());
//                        提交事物-响应服务器
                        session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(100 * 1000);

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
