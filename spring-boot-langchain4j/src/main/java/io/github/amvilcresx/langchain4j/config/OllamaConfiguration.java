package io.github.amvilcresx.langchain4j.config;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilder;
import dev.langchain4j.model.chat.Capability;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import io.github.amvilcresx.langchain4j.annotation.ConditionalOnAiUsed;
import io.github.amvilcresx.langchain4j.common.AiMode;
import io.github.amvilcresx.langchain4j.config.ollama.OllamaConfigProperties;
import io.github.amvilcresx.langchain4j.service.ConsultantService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnAiUsed(use = AiMode.OLLAMA)
@EnableConfigurationProperties(OllamaConfigProperties.class)
public class OllamaConfiguration {


    /**
     * 这里的Bean的名称，需要与langchain4j已经集成的 openai的bean名称一致，否则变更策略时，就会找不到bean
     *
     * @see ConsultantService
     */
    @Bean(name = "openAiChatModel")
    public ChatModel chatModel(OllamaConfigProperties configProperties) {
        OllamaConfigProperties.ChatModelProperties chatModelProps = configProperties.getChatModel();

        return OllamaChatModel.builder()
                .httpClientBuilder(new SpringRestClientBuilder())
                .baseUrl(chatModelProps.getBaseUrl())
                .modelName(chatModelProps.getModelName())
                .logRequests(chatModelProps.getLogRequests())
                .logResponses(chatModelProps.getLogResponses())
                .maxRetries(chatModelProps.getMaxRetries())
                .temperature(chatModelProps.getTemperature())
                .timeout(chatModelProps.getTimeout())
                .customHeaders(chatModelProps.getCustomHeaders())
                .build();
    }


    /**
     * 这里的Bean的名称，需要与langchain4j已经集成的 openai的bean名称一致，否则变更策略时，就会找不到bean
     *
     * @see ConsultantService
     */
    @Bean(name = "openAiStreamingChatModel")
    public StreamingChatModel openAiStreamingChatModel(OllamaConfigProperties configProperties) {

        OllamaConfigProperties.StreamChatModelProperties streamChatModelProps = configProperties.getStreamingChatModel();

        return OllamaStreamingChatModel.builder()
                .httpClientBuilder(new SpringRestClientBuilder())
                .baseUrl(streamChatModelProps.getBaseUrl())
                .modelName(streamChatModelProps.getModelName())
                .logRequests(streamChatModelProps.getLogRequests())
                .logResponses(streamChatModelProps.getLogResponses())
                .temperature(streamChatModelProps.getTemperature())
                .customHeaders(streamChatModelProps.getCustomHeaders())
                .timeout(streamChatModelProps.getTimeout())
                .build();
    }


    @Bean
    @Primary
    public EmbeddingModel embeddingModel(OllamaConfigProperties configProperties) {
        OllamaConfigProperties.EmbeddingModelProperties embeddingModelProps = configProperties.getEmbeddingModel();
        return OllamaEmbeddingModel.builder()
                .httpClientBuilder(new SpringRestClientBuilder())
                .baseUrl(embeddingModelProps.getBaseUrl())
                .modelName(embeddingModelProps.getModelName())
                .maxRetries(embeddingModelProps.getMaxRetries())
                .timeout(embeddingModelProps.getTimeout())
                .customHeaders(embeddingModelProps.getCustomHeaders())
                .build();
    }

}
