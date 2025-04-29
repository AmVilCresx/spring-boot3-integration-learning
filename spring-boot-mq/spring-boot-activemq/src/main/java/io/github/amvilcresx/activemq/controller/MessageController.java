package io.github.amvilcresx.activemq.controller;

import io.github.amvilcresx.activemq.message.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private MessageSender sender;

    @GetMapping("/send")
    public String sendMsg(String msg, String type) {
        if (Objects.equals(type, "topic")) {
            sender.sendTopicMessage(msg);
        } else {
            sender.sendQueueMessage(msg);
        }
        return "ok" + type;
    }
}
