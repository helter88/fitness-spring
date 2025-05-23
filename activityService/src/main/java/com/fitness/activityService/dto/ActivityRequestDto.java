package com.fitness.activityService.dto;

import com.fitness.activityService.model.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

public record ActivityRequestDto(
        String userId,
        ActivityType type,
        Integer duration,
        Integer caloriesBurned,
        LocalDateTime startTime,
        Map<String, Object> additionalMetrics
) {
}
