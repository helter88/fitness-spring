package com.fitness.activityService.controller;

import com.fitness.activityService.dto.ActivityRequestDto;
import com.fitness.activityService.dto.ActivityResponseDto;
import com.fitness.activityService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    @PostMapping
    public ResponseEntity<ActivityResponseDto> trackActivity(@RequestBody ActivityRequestDto activityRequestDto) {
        return ResponseEntity.ok(activityService.trackActivity(activityRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponseDto>> getUserActivities(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }
}
