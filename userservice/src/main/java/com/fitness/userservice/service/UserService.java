package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequestDto;
import com.fitness.userservice.dto.UserResponseDto;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public UserResponseDto register(RegisterRequestDto registerRequestDto) {
        if(userRepository.existsByEmail(registerRequestDto.email())) {
            throw new RuntimeException("Email already exist");
        }

        User user = new User();
        user.setEmail(registerRequestDto.email());
        user.setPassword(registerRequestDto.password());
        user.setFistName(registerRequestDto.fistName());
        user.setLastName(registerRequestDto.lastName());
        User savedUser =  userRepository.save(user);
        return new UserResponseDto(
                savedUser.getEmail(),
                savedUser.getFistName(),
                savedUser.getLastName()
        );
    }

    public UserResponseDto getUserProfile(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not Found"));
        return new UserResponseDto(
                user.getEmail(),
                user.getFistName(),
                user.getLastName()
        );
    }
}
