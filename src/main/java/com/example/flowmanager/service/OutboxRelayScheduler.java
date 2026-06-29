package com.example.flowmanager.service;

import com.example.flowmanager.kafka.producer.FileProducer;
import com.example.flowmanager.model.event.InputEvent;
import com.example.flowmanager.model.event.OutboxEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxRelayScheduler {
    private final OutboxService outboxService;
    private final FileProducer fileProducer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "${flow-manager.shedlock.outbox-relay-name}", lockAtMostFor = "30S", lockAtLeastFor = "5S")
    public void relay() {
        List<OutboxEvent> events = outboxService.getUnsent();
        for (OutboxEvent event : events) {
            try {
                InputEvent payload = objectMapper.readValue(event.getPayload(), InputEvent.class);
                fileProducer.send(payload).get();
                outboxService.markSent(event.getEventId());
            } catch (Exception e) {
                log.error("Failed to relay outbox event: ", event.getEventId(), e);
            }
        }
    }
}