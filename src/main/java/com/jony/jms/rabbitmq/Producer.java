package com.jony.jms.rabbitmq;

import com.rabbitmq.client.*;

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
//            durable 设置持久化交换器
            channel.exchangeDeclare("direct-exchange","direct");
//          设置queue
            channel.queueDeclare("myQueue",true,false,false,null);

//        声明路由key
            String routeKey = "direct-key";
//        send 生产消息

//            开启确认模式
            channel.confirmSelect();

            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println(deliveryTag);
                    System.out.println(multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println(deliveryTag);
                    System.out.println(multiple);
                }
            });


            //            设置信息属性
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.contentType("text/text-plain");
            channel.basicPublish("direct-exchange",routeKey,builder.build(),"rabbitmq-message".getBytes("UTF-8"));
            channel.basicPublish("direct-exchange",routeKey,builder.build(),"rabbitmq-message2".getBytes("UTF-8"));

//            if(channel.waitForConfirms()){
//                System.out.println("成功发送");
//            }
            //            关闭
//            异步确认的情况下不能断开连接
            Thread.sleep(100*1000);
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send();
    }
}
