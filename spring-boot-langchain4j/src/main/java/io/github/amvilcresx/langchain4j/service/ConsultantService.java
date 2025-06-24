package io.github.amvilcresx.langchain4j.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 手动装配
        chatModel = "openAiChatModel", // 指定模型
        streamingChatModel = "openAiStreamingChatModel",
//        chatMemory = "chatMemory"
        chatMemoryProvider = "chatMemoryProvider",
        contentRetriever = "contentRetriever", // 向量数据库检索对象
        tools = "reservationTool"   // 工具
)

//@AiService // 自动装配
public interface ConsultantService {

    String chat(String message);

    // @SystemMessage("你是厨子") // 可以理解为 【以什么角色回答用户的问题】
   // @SystemMessage(fromResource = "system.txt") // 可以理解为 【以什么角色回答用户的问题】
    //@UserMessage("你是厨子{{msg}}")
//    Flux<String> chatFlux(@V("msg") String message);
    Flux<String> chatFlux(@MemoryId String memoryId,  @UserMessage String message);
}
