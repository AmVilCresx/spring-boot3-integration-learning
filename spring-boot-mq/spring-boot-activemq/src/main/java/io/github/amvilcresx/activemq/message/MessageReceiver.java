package io.github.amvilcresx.activemq.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListenerContainerFactory")
    public void firstReceiveQueueMessage(String message) {
        System.out.println("111---收到了点对点（只能有一个收到）: " + message);
    }

    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListenerContainerFactory")
    public void secondReceiveQueueMessage(String message) {
        System.out.println("222---收到了点对点（只能有一个收到）: " + message);
    }

    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListenerContainerFactory")
    public void firstReceiveTopicMessage(String message) {
        System.out.println("first---收到了topic队列的消息（都收到）：" + message);
    }

    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListenerContainerFactory")
    public void secondReceiveTopicMessage(String message) {
        System.out.println("second---收到了topic队列的消息（都收到）：" + message);
    }
}
