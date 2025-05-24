package com.fitness.activityService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient webClient;

    public boolean validateUser(UUID userId) {
        try {
            return Boolean.TRUE.equals(webClient.get()
                    .uri("/api/users/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RuntimeException("User Not Found: " + userId);
            }
        }
        return false;
    }
}
