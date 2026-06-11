package com.example.flowmanager.service;

import com.example.flowmanager.exception.SaveOutboxException;
import com.example.flowmanager.model.entity.EventType;
import com.example.flowmanager.model.event.InputEvent;
import com.example.flowmanager.model.event.OutboxEvent;
import com.example.flowmanager.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void saveEvent(EventType eventType, InputEvent payload) {
        try {
            outboxRepository.save(new OutboxEvent(null, eventType, objectMapper.writeValueAsString(payload), LocalDateTime.now(), null));
        } catch (Exception e) {
            throw new SaveOutboxException(e);
        }
    }

    public List<OutboxEvent> getUnsent() {
        return outboxRepository.findBySentAtIsNull();
    }

    @Transactional
    public void markSent(Long id) {
        outboxRepository.findById(id).ifPresent(s -> {
            s.setSentAt(LocalDateTime.now());
            outboxRepository.save(s);
        });
    }
}
