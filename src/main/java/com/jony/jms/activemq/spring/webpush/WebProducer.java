package com.jony.jms.activemq.spring.webpush;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.StompConnection;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
/**
 * 基于 stomp
 */
public class WebProducer {

    public void sendWeb(){
        StompConnection connection = new StompConnection();
        try {
            connection.open("localhost",61613);
//            建立连接
            connection.connect(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);

//            发送消息 topic 表示主题订阅模式
            connection.send("/topic/shopping-discount","test-msg-web");

            connection.disconnect();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebProducer webProducer = new WebProducer();
        webProducer.sendWeb();
    }
}
