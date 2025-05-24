package com.fitness.activityService.dto;

import com.fitness.activityService.model.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record ActivityResponseDto(
        String id,
        UUID userId,
        ActivityType type,
        Integer duration,
        Integer caloriesBurned,
        LocalDateTime startTime,
        Map<String, Object> additionalMetrics,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
