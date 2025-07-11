package io.github.amvilcresx.langchain4j.condition;

import io.github.amvilcresx.langchain4j.annotation.ConditionalOnAiUsed;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class OnAiCondition implements Condition {

    private final static String AI_MODE_KEY = "langchain4j.ai-mode";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String configMode = context.getEnvironment().getProperty(AI_MODE_KEY);
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnAiUsed.class.getName());
        String expectedUse = String.valueOf(attributes.get("use"));

        return StrUtil.equalsIgnoreCase(configMode, expectedUse);
    }
}
