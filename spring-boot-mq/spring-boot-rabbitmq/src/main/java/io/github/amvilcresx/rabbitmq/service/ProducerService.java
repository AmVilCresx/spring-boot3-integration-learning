package io.github.amvilcresx.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String routingKey, String message) {
        //                            交换机名称          路由key      消息内容（Object）
        rabbitTemplate.convertAndSend("direct.exchange", routingKey, message);

        /*
        【 需要开启生产者确认机制 】

        String msgId = IdUtil.fastUUID();
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend("direct.exchange", routingKey, message, correlationData);
        log.info("向直连交换机发送消息， routing key={}, message={}", routingKey, message);

        // 异步确认回调
        rabbitTemplate.setConfirmCallback((corrData, ack, cause) -> {
            if (ack) {
               log.info("Message 已确认, ID={}", corrData.getId());
            } else {
                log.error("Message 确认失败, ID={}，Cause={}", corrData.getId(), cause);
                // 可在此处实现重发逻辑
            }
        });

        // 消息路由失败回调（如无法路由到队列）
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("消息路由失败： Message={}, ReplyCode={}, ReplyText={}",
                    returned.getMessage(), returned.getReplyCode(), returned.getReplyText());
        });

         */
    }

}
