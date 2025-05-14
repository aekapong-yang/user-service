package com.example.userservice.factory;

import com.example.userservice.constant.ErrorCode;
import com.example.userservice.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {
    public static <T> ResponseEntity<ApiResponse<T>> success() {
        var responseCode = ErrorCode.SUCCESS;
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(responseCode.name())
                        .message(responseCode.getMessage())
                        .data(null)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        var responseCode = ErrorCode.SUCCESS;
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(responseCode.name())
                        .message(responseCode.getMessage())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<T>builder()
                        .code(code)
                        .message(message)
                        .data(null)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .data(null)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .code(errorCode.name())
                        .message(message)
                        .data(null)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatusCode httpStatusCode) {
        return ResponseEntity
                .status(httpStatusCode)
                .body(ApiResponse.<T>builder()
                        .code(ErrorCode.GENERAL_ERROR.getCode())
                        .message(message)
                        .data(null)
                        .build());
    }
}
