package com.jony.jms.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public void consumer() {
//        创建工厂设置用户、密码、地址
        ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        //        创建连接
        factory.setVirtualHost("/");
        try {
            Connection connection = factory.newConnection();
//        创建信道
            Channel channel = connection.createChannel();
//        声明交换器、声明路由key
            channel.exchangeDeclare("direct-exchange","direct");
            String routeKey = "direct-key";
//            声明队列（获取信息）
            String queueName = channel.queueDeclare().getQueue();
//            绑定队列,通过路由键将队列和交换器进行绑定
            channel.queueBind(queueName,"direct-exchange",routeKey);
            //        获取信息
            while (true){
//                消费消息
                boolean autoAck = false;
                String consumerTag = "";
//                添加监听获取消息
                channel.basicConsume(queueName,autoAck,consumerTag,new DefaultConsumer(channel){
                    @Override
//                    处理收到的信息
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                        信封上包涵路由key、传输tag、交换器名称
                        String routeKey = envelope.getRoutingKey();
                        envelope.getDeliveryTag();
                        envelope.getExchange();
//                        properties 包涵数据属性信息
                        System.out.println(properties.getContentType());
//                      输出body消息
                        System.out.println(new String(body,"UTF-8"));

                    }
                });
            }
//        关闭信道与连接

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.consumer();
    }
}
