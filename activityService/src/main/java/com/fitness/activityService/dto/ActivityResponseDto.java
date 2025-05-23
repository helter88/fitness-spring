package com.fitness.activityService.dto;

import com.fitness.activityService.model.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

public record ActivityResponseDto(
        String id,
        String userId,
        ActivityType type,
        Integer duration,
        Integer caloriesBurned,
        LocalDateTime startTime,
        Map<String, Object> additionalMetrics,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
