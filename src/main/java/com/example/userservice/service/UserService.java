package com.example.userservice.service;

import com.example.userservice.constant.ErrorCode;
import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.user.*;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.factory.ResponseFactory;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.spec.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse<GetUserAllResponse>> getUserAll(GetUserAllRequest request) {
        Page<User> users = userRepository.findAll(UserSpecification.findUserAll(request), request.getPageable());
        return ResponseFactory.success(GetUserAllResponse.builder()
                .items(users.stream()
                        .map(user -> GetUserAllResponse.User.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .build())
                        .toList())
                .page(users.getNumber() + 1)
                .size(users.getNumberOfElements())
                .totalPages(users.getTotalPages())
                .totalElements((int) users.getTotalElements())
                .build());
    }

    public ResponseEntity<ApiResponse<GetUserByIdResponse>> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return ResponseFactory.success(GetUserByIdResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build());
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> updateUserById(PutUserByIdRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return ResponseFactory.success();
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> createUser(PostCreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password("")
                .build();
        userRepository.save(user);
        return ResponseFactory.success(Map.of("id", user.getId()), ErrorCode.SUCCESS);
    }
}
