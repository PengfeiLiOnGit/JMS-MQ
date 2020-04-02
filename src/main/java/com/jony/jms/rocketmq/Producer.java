package com.jony.jms.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.Charset;

public class Producer {

    /**
     * 创建生产工厂
     * 设置地址
     * 设置其他参数
     * 发送消息
     */
    public void send() {

//        创建生产者（指定生产者组）
        DefaultMQProducer producer = new DefaultMQProducer("group_producer");
//        指定nameserver 地址 9876 默认端口号
        producer.setNamesrvAddr("localhost:9876");
//        启动客户端生产者实例
        try {
            producer.start();
//        发送消息
            for (int i = 0; i < 5; i++) {
                Message message = new Message();
                message.setTopic("topic_java_test");
                message.setTags("tage-a");
                message.setBody(("RocketMQ Msg:"+i).getBytes(Charset.forName("UTF-8")));
                //            返回发送消息的结果
                SendResult sendResult = producer.send(message);
                System.out.printf("%s%n",sendResult);
            }
            //        关闭连接
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send();
    }
}
