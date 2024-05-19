package org.example.config;

import org.example.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(LoggingAspect.class)
@ConditionalOnProperty(prefix = "spring.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
