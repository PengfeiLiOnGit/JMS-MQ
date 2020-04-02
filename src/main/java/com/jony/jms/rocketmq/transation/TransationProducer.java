package com.jony.jms.rocketmq.transation;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class TransationProducer {
    public void send(){
//        生产者
        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_group");
//        地址
        producer.setNamesrvAddr("localhost:9876");
//      设置事务处理线程
        ExecutorService service = Executors.newCachedThreadPool();
//        设置本地事务执行的线程池
        producer.setExecutorService(service);
//        设置事务监听器
        producer.setTransactionListener(new TransactionListener() {
//            执行本地事务
            @Override
            /**
             * UNKNOW 未知状态
             * ROLLBACK_MESSAGE 回退
             * COMMIT_MESSAGE 正确提交
             */
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (message.getTags().equals("trans-1")){
                    System.out.println(o);
//                    模拟事务失败
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            /**
             * 二次确认检查
             */
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
//                针对重复消息可以使用keys 进行排他检查或者通过其他参数
                System.out.println("二次确认检查"+messageExt.getKeys());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

//        设置事务后开启生产消息
        DefaultMQProducer mqProducer = new DefaultMQProducer("transaction-group");
        mqProducer.setNamesrvAddr("localhost:9876");
        try {
//            producer.start();
            mqProducer.start();
            for (int i = 0; i < 2; i++) {
                Message message = new Message("TopicTransaction","trans-"+i,"#trans-"+i,("#trans-body"+i).getBytes());
//                message.setTopic("topic_trans_topic");
//                message.setTags("trans-"+i);
//                message.setKeys("#trans-"+i);
//                message.setBody(("#trans-body"+i).getBytes("UTF-8"));

//                SendResult sendResult = producer.sendMessageInTransaction(message,"test-object");
                SendResult sendResult = mqProducer.send(message);
                try {
                    sendResult = producer.send(message);
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                }
                System.out.println(sendResult.getMsgId());
            }

            Thread.sleep(100 * 1000);
//            关闭连接
//            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TransationProducer producer = new TransationProducer();
        producer.send();
    }
}
