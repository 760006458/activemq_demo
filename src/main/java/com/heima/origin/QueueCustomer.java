package com.heima.origin;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;


/**
 * xuan
 * 2018/3/5
 */
public class QueueCustomer {
    @Test
    public void testQueue() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue-test");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testTopic() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        Topic topic = session.createTopic("topic-test1");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicPersist() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("0002");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("topic-test-persist3");
        TopicSubscriber consumer = session.createDurableSubscriber(topic, "haha");
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
//        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
