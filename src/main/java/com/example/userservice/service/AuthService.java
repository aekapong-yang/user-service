package com.example.userservice.service;

import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.auth.PostAuthResponse;
import com.example.userservice.factory.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    public ResponseEntity<ApiResponse<PostAuthResponse>> auth(String userId) {
        return ResponseFactory.success();
    }
}
