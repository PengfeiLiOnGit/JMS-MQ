import com.jony.jms.rocketmq.order.Consumer;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-rocket-order-consumer.xml"})
public class RocketMqOrderConTest {
    @Autowired
    private Consumer consumer;

    @Test
    public void testActive() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Thread.sleep(200*1000);
        consumer.destroy();
    }
}
