package io.github.amvilcresx.langchain4j.annotation;

import io.github.amvilcresx.langchain4j.common.AiMode;
import io.github.amvilcresx.langchain4j.condition.OnAiCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnAiCondition.class)
public @interface ConditionalOnAiUsed {

    AiMode use() default AiMode.OPENAI;
}
