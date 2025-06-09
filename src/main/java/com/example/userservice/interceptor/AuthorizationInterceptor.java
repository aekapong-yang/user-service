package com.example.userservice.interceptor;

import com.example.userservice.annotation.NoAuthRequired;
import com.example.userservice.constant.ErrorCode;
import com.example.userservice.context.MethodExecutionContext;
import com.example.userservice.dto.ApiContext;
import com.example.userservice.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final ApiContext apiContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        boolean isPublic = handlerMethod.getMethod().isAnnotationPresent(NoAuthRequired.class) ||
                handlerMethod.getBeanType().isAnnotationPresent(NoAuthRequired.class);

        if (isPublic) {
            return true;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if ("Bearer ben".equals(token)) {
            String userRole = request.getHeader("X-Role");
            apiContext.setUserRoles(List.of(userRole));
            apiContext.setUserId("ben");

            return true;
        }

        throw new BusinessException(ErrorCode.UNAUTHORIZED);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MethodExecutionContext.remove();
    }
}
