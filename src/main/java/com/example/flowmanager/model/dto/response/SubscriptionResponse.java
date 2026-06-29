package com.example.flowmanager.model.dto.response;

import com.example.flowmanager.model.entity.SubscriptionType;

import java.time.LocalDate;

public record SubscriptionResponse(SubscriptionType type, LocalDate expiresAt) {
}
