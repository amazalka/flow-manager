package com.example.flowmanager.kafka.consumer;

import com.example.flowmanager.model.event.SubscriptionChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Service
@RequiredArgsConstructor
public class SubscriptionCacheConsumer {
    private final CacheManager cacheManager;

    @KafkaListener(topics = "${flow-manager.kafka.topics.subscription-changed}", groupId = "flow-manager", properties = {"spring.json.value.default.type=com.example.flowmanager.model.event.SubscriptionChangedEvent"})
    public void handle(SubscriptionChangedEvent event) {
        log.info("Received cache invalidation event for login: {}", event.login());
        Cache cache = cacheManager.getCache("subscriptions");
        if (cache != null) {
            cache.evict(event.login());
            log.info("Cache entry for login '{}' evicted successfully", event.login());
        } else {
            log.warn("Cache 'subscriptions' not found in CacheManager");
        }
    }
}
