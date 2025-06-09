package com.example.userservice.controller;

import com.example.userservice.annotation.NoAuthRequired;
import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.auth.PostAuthResponse;
import com.example.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @NoAuthRequired
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<PostAuthResponse>> getUserById(@RequestParam String userId) {
        return authService.auth(userId);
    }
}
