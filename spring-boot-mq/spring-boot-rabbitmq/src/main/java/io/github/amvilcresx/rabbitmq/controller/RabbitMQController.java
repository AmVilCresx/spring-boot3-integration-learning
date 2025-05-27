package io.github.amvilcresx.rabbitmq.controller;

import io.github.amvilcresx.rabbitmq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amqp")
public class RabbitMQController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/send2direxc")
    public String sendMsgToDirectExchange(String routingKey, String message) {
        producerService.sendMessageToDirect(routingKey, message);
        return "ok direxc: " + routingKey;
    }

    @GetMapping("/send2Topic")
    public String sendMsgToTopicExchange(String routingKey, String message) {
        producerService.sendMessageToTopic(routingKey, message);
        return "ok topic :"  + routingKey;
    }

    @GetMapping("/send2Fanout")
    public String sendMsgToFanoutExchange(String message) {
        producerService.sendMessageToFanout(message);
        return "ok fanout! ";
    }

    @GetMapping("/mockDeadLetter")
    public String mockDeadLetter(String message) {
        producerService.sendMessageForMockDeadLetter(message);
        return "ok mockDeadLetter! ";
    }
}
