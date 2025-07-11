package io.github.amvilcresx.langchain4j.config.ollama;

import io.github.amvilcresx.langchain4j.common.LangChain4jConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "langchain4j." + LangChain4jConstants.OLLAMA_PROVIDER)
public class OllamaConfigProperties {

    @NestedConfigurationProperty
    private ChatModelProperties chatModel;

    @NestedConfigurationProperty
    private StreamChatModelProperties streamingChatModel;

    @NestedConfigurationProperty
    private EmbeddingModelProperties embeddingModel;

    @Getter
    @Setter
    public static class ChatModelProperties {

        private String baseUrl = "http://localhost:11434";

        private String modelName;

        private Map<String, String> customHeaders = new HashMap<>();

        private Boolean logRequests = false;

        private Boolean logResponses = false;

        private Duration timeout = Duration.ofSeconds(10L);

        private Integer maxRetries = 3;

        private Double temperature = 0.7D;
    }

    @Getter
    @Setter
    public static class StreamChatModelProperties {

        private String baseUrl = "http://localhost:11434";

        private String modelName;

        private Boolean logRequests = false;

        private Boolean logResponses = false;

        private Map<String, String> customHeaders = new HashMap<>();

        private Duration timeout = Duration.ofSeconds(10L);

        private Integer maxRetries = 3;

        private Double temperature = 0.7D;
    }

    @Getter
    @Setter
    public static class EmbeddingModelProperties {

        private String baseUrl = "http://localhost:11434";

        private String modelName;

        private Map<String, String> customHeaders = new HashMap<>();

        private Integer maxRetries = 3;

        private Duration timeout = Duration.ofSeconds(10L);

        private Boolean logRequests = false;

        private Boolean logResponses = false;
    }

}
