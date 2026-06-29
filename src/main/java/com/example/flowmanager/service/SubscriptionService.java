package com.example.flowmanager.service;

import com.example.flowmanager.model.client.SubscriptionClient;
import com.example.flowmanager.model.dto.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionClient subscriptionClient;

    @Cacheable(value = "subscriptions", key = "#login")
    public SubscriptionResponse getSubscription (String login) {
        log.info("Cache MISS for login: {}. Fetching from Subscription Service...", login);
        return subscriptionClient.getSubscription(login);
    }
}
