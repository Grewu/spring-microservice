package org.example.config;

import org.example.handler.GlobalHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = "spring.exception.handling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    public GlobalHandlerAdvice globalHandlerAdvice() {
        return new GlobalHandlerAdvice();
    }
}
