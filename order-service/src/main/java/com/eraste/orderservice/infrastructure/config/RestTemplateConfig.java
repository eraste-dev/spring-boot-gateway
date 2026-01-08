package com.eraste.orderservice.infrastructure.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate used in service-to-service communication.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a RestTemplate bean.
     *
     * @param builder the RestTemplateBuilder
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
