package com.heima.origin;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * xuan
 * 2018/3/5
 */
public class QueueProducer {
    @Test
    public void testQueue() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start(); //老忘记开启连接

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue-test1");
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage("原生Queue测试");
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopic() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("topic-test2");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("原生topic测试");
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 默认topic是不会在服务端持久化存储的，生产消息之后，如果没有消费者处于监听状态，那么发布的消息就会丢失
     * @throws JMSException
     */
    @Test
    public void testTopicPersist() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.100:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("topic-test-persist3");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage textMessage = session.createTextMessage("原生topic测试，持久化存储");
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }
}
