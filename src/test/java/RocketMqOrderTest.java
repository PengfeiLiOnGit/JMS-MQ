import com.jony.jms.rocketmq.order.Order;
import com.jony.jms.rocketmq.order.OrderMessageSelector;
import com.jony.jms.rocketmq.order.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-rocket-order.xml"})
public class RocketMqOrderTest {
    @Autowired
    private Producer producer;
    @Autowired
    private OrderMessageSelector orderMessageSelector;

    private String[] status = {"已创建", "已付款", "已取消", "已完成", "已配送"};

    @Test
    public void testActive() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        循环10个order
        for (int i = 1; i < 10; i++) {

            for (int j = 0; j < status.length; j++) {
                Order order = new Order();
                order.setId(i);
                order.setContent("order-content");
                order.setSendOrder(j);
                order.setStatus(status[j]);
//编辑消息
                Message message = new MessageExt();
//                设置主题
                message.setTopic("topic_order_test");
//                设置二级标签
                message.setTags("tage-order"+status[j]);
//                设置唯一标识key
                message.setKeys("#orderid="+i+";sendOrder="+j);
//                设置消息体
                message.setBody(order.toString().getBytes("UTF-8"));
//                生产消息（消息，消息队列分配过滤器，其他参数）
//
                SendResult result = producer.getProducer().send(message, orderMessageSelector,i );
                System.out.printf(result.toString());
            }
        }

    }
}
