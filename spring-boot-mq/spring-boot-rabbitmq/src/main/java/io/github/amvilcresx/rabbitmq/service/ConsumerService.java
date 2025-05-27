package io.github.amvilcresx.rabbitmq.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ConsumerService {

    /* ===================== 【Direct Exchange 监听者】 ============================== */

    // 监听队列1，路由键为 routing.key1
    @RabbitListener(queues = "direct.queue1")
    public void handleDirectMessage1(String message, Channel channel, Message amqpMessage) throws IOException {
        log.info("直连交换机的 direct.queue1 -----11111 队列收到消息：【{}】", message);
        /*

       // 需要配合 消费这开启手动确认模式  使用
        try {
            // 确认消息（第二个参数 multiple 表示是否批量确认）
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // 拒绝消息（第三个参数 requeue 是否重新入队）
            channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            // 或发送到死信队列
            // channel.basicReject(deliveryTag, false);
            // rabbitTemplate.convertAndSend("dlx.exchange", "dlx.routing.key", message);
        }

        * */

    }

    // 监听队列2，路由键为 routing.key2
    @RabbitListener(queues = "direct.queue2")
    public void handleDirectMessage2(String message) {
        log.info("直连交换机的 direct.queue2 -----22222 队列收到消息：【{}】", message);
    }




    /* ===================== 【Topic Exchange 监听者】 ============================== */
    @RabbitListener(queues = "topic.queue1")
    public void handleTopicMessage1(String message) {
        log.info("topic 交换机的 topic.queue1 ----- 11111 队列收到消息：【{}】", message);
    }

    @RabbitListener(queues = "topic.queue2")
    public void handleTopicMessage2(String message) {
        log.info("topic 交换机的 topic.queue2 ----- 222222 队列收到消息：【{}】", message);
    }


    /* ===================== 【 Fanout Exchange 监听者】 ============================== */

    @RabbitListener(queues = "fanout.queue1")
    public void handleFanoutMessage1(String message) {
        log.info("Fanout 交换机的 fanout.queue1 ----- 11111 队列收到消息：【{}】", message);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void handleFanoutMessage2(String message) {
        log.info("Fanout 交换机的 fanout.queue2 ----- 2222 队列收到消息：【{}】", message);
    }


    @RabbitListener(queues = "business_queue")
    public void receiveBusiness(Message message,  Channel channel) throws IOException {
        System.out.println("Business Consumer received: " + message);
        // 模拟处理异常，触发死信
        try {
            throw new RuntimeException("Simulated error");
        }catch (Exception e) {
           channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 不重新排队
        }

    }

    // 死信队列 消费者
    @RabbitListener(queues = "dead_letter_queue")
    public void receiveDead(String message) {
        log.info("***  死信队列 ***** 收到消息：【{}】", message);
    }

}
