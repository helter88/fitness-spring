package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;

import java.time.LocalDateTime;

public record UserResponseDto (
        String email,
        String fistName,
        String lastName
) {
}
