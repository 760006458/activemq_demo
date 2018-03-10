package com.heima.transaction_cluster;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * 测试发现：session使用事务机制比自动应答机制快50被以上。先留着这个问题
 * xuan
 * 2018/3/5
 */
public class QueueProducer {
    @Test
    public void testQueue() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "failover:(tcp://192.168.25.100:61617,tcp://192.168.25.100:61618,tcp://192.168.25.100:61619)?randomize=false");
        Connection connection = connectionFactory.createConnection();
        connection.start(); //老忘记开启连接
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        MessageProducer producer = null;
        int i = 0;
        long start = System.currentTimeMillis();
        while (i < 1000) {

            Queue queue = session.createQueue("queue-test1");
            producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("原生Queue测试");
            producer.send(textMessage);
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        producer.close();
        session.commit();
//        session.close();
        connection.close();
    }

}
