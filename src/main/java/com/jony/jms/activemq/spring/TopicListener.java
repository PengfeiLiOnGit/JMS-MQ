package com.jony.jms.activemq.spring;

        import org.springframework.stereotype.Component;

        import javax.jms.JMSException;
        import javax.jms.Message;
        import javax.jms.MessageListener;
        import java.lang.annotation.ElementType;
        import java.util.Enumeration;

@Component("topic-listener")
public class TopicListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(message);
        try {
            Enumeration en = message.getPropertyNames();
            while (en.hasMoreElements()){
                System.out.println(en.nextElement());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
