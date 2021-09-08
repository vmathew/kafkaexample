package com.cdf.kengine.service;

import com.cdf.kengine.config.PropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    PropertyResolver propertyResolver;

    @Autowired
    public ProducerService(KafkaTemplate<String, String> kafkaTemplate, PropertyResolver propertyResolver) {
        this.kafkaTemplate = kafkaTemplate;
        this.propertyResolver = propertyResolver;
    }

    public void sendMessage(String message){
        logger.info(String.format("<>||<> => Producing message: %s", message));
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(propertyResolver.getTopicName(), message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[ {} ] due to : {}", message, ex.getMessage());
            }
        });
    }
}
