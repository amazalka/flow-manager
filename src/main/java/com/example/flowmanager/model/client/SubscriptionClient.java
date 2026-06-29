package com.example.flowmanager.model.client;

import com.example.flowmanager.model.dto.response.SubscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${service.subscription.name}")
public interface SubscriptionClient {
    @GetMapping("/api/v1/subscriptions")
    SubscriptionResponse getSubscription (@RequestHeader("X-User-Login") String login);
}
