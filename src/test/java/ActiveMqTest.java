import com.jony.jms.activemq.spring.Producer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ActiveMqTest {
    @Autowired
    private Producer producer;

    @Test
    public void testActive(){
        producer.sendMessage();
        producer.sendTopic();
    }
}
