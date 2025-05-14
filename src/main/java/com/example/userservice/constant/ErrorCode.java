package com.example.userservice.constant;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /***
     * success
     */
    SUCCESS(Code.SUCCESS, "success", HttpStatus.OK),

    /***
     * client error
     */
    INVALID_PARAMETER(Code.INVALID_PARAMETER, "invalid parameter", HttpStatus.BAD_REQUEST),

    /***
     * resource not found
     */
    USER_NOT_FOUND(Code.SUCCESS, "user not found", HttpStatus.NOT_FOUND),

    /***
     * internal server error
     */
    GENERAL_ERROR(Code.GENERAL_ERROR, "general error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR(Code.SERVER_ERROR, "user not found", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Code {
        public static final String SUCCESS = "success";
        public static final String NOT_FOUND = "data_not_found";
        public static final String INVALID_PARAMETER = "invalid_parameter";
        public static final String UNAUTHORIZED = "unauthorized";
        public static final String FORBIDDEN = "forbidden";
        public static final String SERVER_ERROR = "server_error";
        public static final String GENERAL_ERROR = "general_error";
    }
}
