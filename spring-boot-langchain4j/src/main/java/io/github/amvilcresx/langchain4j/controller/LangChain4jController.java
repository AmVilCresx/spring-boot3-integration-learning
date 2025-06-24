package io.github.amvilcresx.langchain4j.controller;

import io.github.amvilcresx.langchain4j.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/lc4j")
public class LangChain4jController {

    @Autowired
    private ConsultantService consultantService;


    @RequestMapping("/chat")
    public String chat(String question) {
        return consultantService.chat(question);
    }

    @RequestMapping(value = "/chatStream",produces = "text/html;charset=utf-8")
    public Flux<String> chatStream(String memoryId, String question) {
        return consultantService.chatFlux(memoryId, question);
    }
}
