package com.jony.jms.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public void send(){
//        建立工厂(rabbit工厂)（用户名、密码）
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //        地址
        factory.setHost("localhost");

//        设置vhost - 一个消息系统中可以有多个vhost 彼此隔离
        factory.setVirtualHost("/");
        //        建立连接
        try {
            Connection connection = factory.newConnection();
//        创建channel信道（一个vhost 中存在多个信道，通过信道双向传输,信道的概念有点类似session 但具体职责略有不同）
            Channel channel = connection.createChannel();
//        声明交换器（在信道）
            channel.exchangeDeclare("direct-exchange","direct");
//        声明路由key
            String routeKey = "direct-key";
//        send 生产消息
            channel.basicPublish("direct-exchange",routeKey,null,"rabbitmq-message".getBytes("UTF-8"));

//            关闭
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send();
    }
}
