package com.example.userservice.service;

import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.user.GetUserAllRequest;
import com.example.userservice.dto.user.GetUserAllResponse;
import com.example.userservice.dto.user.GetUserByIdResponse;
import com.example.userservice.factory.ResponseFactory;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse<GetUserAllResponse>> getUserAll(GetUserAllRequest request) {
        List<User> users = userRepository.findByUsernameStartingWith(request.getUsername());
        return ResponseFactory.success(GetUserAllResponse.builder()
                .users(users.stream()
                        .map(user -> GetUserAllResponse.User.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .build())
                        .toList())
                .build());
    }

    public ResponseEntity<ApiResponse<GetUserByIdResponse>> getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("data not found"));
        return ResponseFactory.success(GetUserByIdResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build());
    }
}
