import com.jony.jms.rocketmq.spring.Producer;
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

import java.nio.charset.Charset;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-rocket.xml"})
public class RocketMqTest {
    @Autowired
    private Producer producer;

    @Test
    public void testActive(){
        try {
            Message message = new MessageExt();
            message.setTopic("topic_java");
            message.setTags("tage-a");
            message.setBody(("RocketMQ Msg:").getBytes(Charset.forName("UTF-8")));
            SendResult result = producer.getProducer().send(message);
            System.out.printf(result.toString());
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
