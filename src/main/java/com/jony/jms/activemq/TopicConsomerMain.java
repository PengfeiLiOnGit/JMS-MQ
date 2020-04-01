package com.jony.jms.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 主题
 */
public class TopicConsomerMain {
    //    默认用户名
    public static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;

    //    默认密码
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void consumer(){
//        工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        try {
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

//            目的地
            Topic topic = session.createTopic("topic-test");
//            消费者
            MessageConsumer consumer = session.createConsumer(topic);
//            订阅消费消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage msg = (TextMessage) message;
                    try {
                        System.out.println("1L"+msg.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            });

            MessageConsumer consumer1 = session.createConsumer(topic);
            consumer1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage msg = (TextMessage) message;
                    try {
                        System.out.println("2L"+msg.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
