package com.example.flowmanager.kafka.consumer;

import com.example.flowmanager.model.event.OutputEvent;
import com.example.flowmanager.service.FileRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileConsumer {
    private final FileRequestService fileRequestService;

    @KafkaListener(topics = "${flow-manager.kafka.topics.output}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(OutputEvent event) throws Exception {
        if (event.getFilePath() != null) {
            fileRequestService.markSuccess(event.getEventId(), event.getFilePath());
        } else {
            fileRequestService.markFailed(event.getEventId());
        }
    }
}
