package io.github.amvilcresx.langchain4j.config;

import io.github.amvilcresx.langchain4j.common.AiMode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.*;

public class ExcludeEnvPostProcessor implements EnvironmentPostProcessor {

    private static final String AI_MODE_KEY = "langchain4j.ai-mode";

    private static final String SPRING_EXCLUDE_KEY = "spring.autoconfigure.exclude";

    private static final String[] CUSTOM_EXCLUDES = {"dev.langchain4j.openai.spring.AutoConfig"};

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 拿到条件
        String condition = environment.getProperty(AI_MODE_KEY);
        if (StringUtils.equalsIgnoreCase(condition, AiMode.OPENAI.toString())) {
            return;
        }

        // 排除掉 activemq 自动装配
        Binder binder = Binder.get(environment);
        List<String> targetExcludes = new ArrayList<>();
        // org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.getExcludeAutoConfigurationsProperty
        List<String> oldExcludes = binder.bind(SPRING_EXCLUDE_KEY, String[].class).map(Arrays::asList).orElse(Collections.emptyList());
        // 原来排除的
        targetExcludes.addAll(oldExcludes);
        // 新增需要排除的
        targetExcludes.addAll(Arrays.asList(CUSTOM_EXCLUDES));

        MutablePropertySources propertySources = environment.getPropertySources();
        Properties prop = new Properties();
        prop.put(SPRING_EXCLUDE_KEY, String.join(",", targetExcludes));
        propertySources.addFirst(new PropertiesPropertySource("customExcludeProperties", prop));
    }
}
