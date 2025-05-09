package com.example.userservice.controller;

import com.example.userservice.dto.ApiContext;
import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.user.*;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final ApiContext apiContext;

    @GetMapping
    public ResponseEntity<ApiResponse<GetUserAllResponse>> getUserAll(
            GetUserAllRequest request) {
        return userService.getUserAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetUserByIdResponse>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateUserById(
            @PathVariable Long id,
            @RequestBody @Valid PutUserByIdRequest request) {
        request.setId(id);
        apiContext.setUserId(UUID.randomUUID().toString());
        return userService.updateUserById(request);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createUser(
            @RequestBody @Valid PostCreateUserRequest request) {
        apiContext.setUserId(UUID.randomUUID().toString());
        return userService.createUser(request);
    }
}
