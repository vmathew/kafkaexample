package com.cdf.kengine.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class PropertyResolver {
    @Value("${spring.kafka.consumer.bootstrap-servers}") private String bootstrapHost;

    @Value("${app.topic-name}") private String topicName;
}
