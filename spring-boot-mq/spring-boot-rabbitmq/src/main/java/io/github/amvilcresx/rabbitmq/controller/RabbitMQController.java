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
        producerService.sendMessage(routingKey, message);
        return "ok direxc";
    }
}
