package io.github.amvilcresx.activemq.message;

import jakarta.jms.Topic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Value("${spring.activemq.queue-name}")
    private String p2pQueue;

    @Value("${spring.activemq.topic-name}")
    private String topicQueue;

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public void sendQueueMessage(String message) {
        jmsTemplate.convertAndSend(p2pQueue, message);
        System.out.println("发送了 queue 消息： '" + message + "'");
    }

    public void sendTopicMessage(String message) {
        // 不能 每次new, 考虑 托管为bean，或 缓存
        Topic topic = new ActiveMQTopic(topicQueue);
        jmsTemplate.convertAndSend(topic, message);
        System.out.println("发送了 topic 消息 '" + message + "'");
    }
}
