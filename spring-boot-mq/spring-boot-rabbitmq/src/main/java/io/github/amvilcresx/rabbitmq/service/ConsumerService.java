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

    // 监听队列1，路由键为 routing.key1
    @RabbitListener(queues = "direct.queue1")
    public void handleMessage1(String message, Channel channel, Message amqpMessage) throws IOException {
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
    public void handleMessage2(String message) {
        log.info("直连交换机的 direct.queue2 -----22222 队列收到消息：【{}】", message);
    }
}
