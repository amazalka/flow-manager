package com.example.flowmanager.kafka.producer;

import com.example.flowmanager.model.event.InputEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileProducer {
    private final KafkaTemplate<String, InputEvent> kafkaTemplate;
    @Value("${flow-manager.kafka.topics.input}")
    private String topic;
    public CompletableFuture<SendResult<String, InputEvent>> send(InputEvent inputEvent) {
        return kafkaTemplate.send(topic, inputEvent);
    }
}
