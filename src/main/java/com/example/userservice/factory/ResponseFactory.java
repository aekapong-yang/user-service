package com.example.userservice.factory;

import com.example.userservice.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {
    public static <T> ResponseEntity<ApiResponse<T>> success() {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .code(2000)
                .message("success")
                .data(null)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .code(2000)
                .message("success")
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(int code, String message) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<T>builder()
                        .code(code)
                        .message(message)
                        .data(null)
                        .build());
    }
}
