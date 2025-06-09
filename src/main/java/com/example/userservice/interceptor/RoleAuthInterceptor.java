package com.example.userservice.interceptor;

import com.example.userservice.annotation.RoleRequired;
import com.example.userservice.constant.ErrorCode;
import com.example.userservice.dto.ApiContext;
import com.example.userservice.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleAuthInterceptor implements HandlerInterceptor {
    private final ApiContext apiContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        RoleRequired roleRequired = method.getMethodAnnotation(RoleRequired.class);
        if (roleRequired == null) {
            roleRequired = method.getBeanType().getAnnotation(RoleRequired.class);
        }

        if (roleRequired != null) {
            List<String> allowedRoles = Arrays.stream(roleRequired.roles())
                    .map(Enum::name)
                    .toList();

            if (apiContext.getUserRoles().stream()
                    .noneMatch(allowedRoles::contains)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
        }

        return true;
    }

}
