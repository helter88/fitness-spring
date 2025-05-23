package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequestDto;
import com.fitness.activityService.dto.ActivityResponseDto;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponseDto trackActivity(ActivityRequestDto activityRequestDto){
        Activity activity = Activity.builder()
                .userId(activityRequestDto.userId())
                .type(activityRequestDto.type())
                .duration(activityRequestDto.duration())
                .caloriesBurned(activityRequestDto.caloriesBurned())
                .startTime(activityRequestDto.startTime())
                .additionalMetrics(activityRequestDto.additionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponseDto(savedActivity);
    }

    public List<ActivityResponseDto> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private ActivityResponseDto mapToResponseDto(Activity activity) {
        return new ActivityResponseDto(
                activity.getId(),
                activity.getUserId(),
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMetrics(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }
}
