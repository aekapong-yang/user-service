package com.example.userservice.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodExecutionContext {
    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    public static void setStartTime(long startTime) {
        startTimeThreadLocal.set(startTime);
    }

    public static long getStartTime() {
        return startTimeThreadLocal.get();
    }

    public static void remove() {
        startTimeThreadLocal.remove();
    }
}
