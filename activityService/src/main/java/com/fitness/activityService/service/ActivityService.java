package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequestDto;
import com.fitness.activityService.dto.ActivityResponseDto;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponseDto trackActivity(ActivityRequestDto activityRequestDto){
        if (!userValidationService.validateUser(activityRequestDto.userId())){
            throw new RuntimeException("Invalid user: " + activityRequestDto.userId());
        }
        Activity activity = Activity.builder()
                .userId(activityRequestDto.userId())
                .type(activityRequestDto.type())
                .duration(activityRequestDto.duration())
                .caloriesBurned(activityRequestDto.caloriesBurned())
                .startTime(activityRequestDto.startTime())
                .additionalMetrics(activityRequestDto.additionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch(Exception e) {
            log.error("Failed to publish activity to RabbitMq: ", e);
        }

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
