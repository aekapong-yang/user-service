package com.example.userservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeaderConstant {
    public static final String X_CORRELATION_ID = "X-Correlation-Id";
    public static final String USER_ID = "User-Id";
}
