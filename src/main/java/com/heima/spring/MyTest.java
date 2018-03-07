package com.heima.spring;

import org.apache.activemq.command.ActiveMQDestination;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * xuan
 * 2018/3/7
 */
public class MyTest {
    @Test
    public void testProducer(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-producer.xml");
        ActiveMQDestination queueDestination = (ActiveMQDestination) applicationContext.getBean("queueDestination");
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        jmsTemplate.send(queueDestination, session -> {
            TextMessage textMessage = session.createTextMessage("spring整合测试：queue");
            return textMessage;
        });
    }

    @Test
    public void testConsumer() throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-consumer.xml");
        //等待
        System.in.read();
    }

}
