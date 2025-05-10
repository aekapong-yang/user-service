package com.example.userservice.filter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {
    private static final String X_CORRELATION_ID = "X-Correlation-Id";
    private static final String USER_ID = "User-Id";
    private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "email");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        addMDCCorrelationId(request, response);
        addMDCUserId(request, response);

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        logRequest(requestWrapper);
        logResponse(responseWrapper, startTime);

        responseWrapper.copyBodyToResponse();
        MDC.clear();
    }

    private void addMDCUserId(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getHeader(USER_ID);
        if (StringUtils.isBlank(userId)) {
            userId = UUID.randomUUID().toString();
        }

        MDC.put(USER_ID, userId);
        response.setHeader(USER_ID, userId);
    }

    private void addMDCCorrelationId(HttpServletRequest request, HttpServletResponse response) {
        String correlationId = request.getHeader(X_CORRELATION_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(X_CORRELATION_ID, correlationId);
        response.setHeader(X_CORRELATION_ID, correlationId);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        byte[] content = request.getContentAsByteArray();
        String requestBody = content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "";
        String maskedBody = maskSensitiveJson(requestBody);
        if (StringUtils.isBlank(request.getQueryString())) {
            log.info("Request: {} {} {}", method, uri, maskedBody);
        } else {
            log.info("Request: {} {}?{} {}", method, uri, queryString, maskedBody);
        }
    }

    private void logResponse(ContentCachingResponseWrapper response, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        int status = response.getStatus();
        String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        String maskedBody = maskSensitiveJson(responseBody);
        String substring = StringUtils.substring(maskedBody, 0, Math.min(maskedBody.length(), 100));
        log.info("Response: {} Body: {}, Duration: {} ms", status, substring, duration);
    }

    private String maskSensitiveJson(String json) {
        Configuration config = Configuration.builder()
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();

        DocumentContext context = JsonPath.using(config).parse(json);
        for (String field : SENSITIVE_FIELDS) {
            context.set("$.." + field, "***");
        }

        return context.jsonString();
    }
}
