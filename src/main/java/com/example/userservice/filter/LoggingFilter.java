package com.example.userservice.filter;

import com.example.userservice.utils.Utils;
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
import java.util.UUID;

import static com.example.userservice.constant.HttpHeaderConstant.USER_ID;
import static com.example.userservice.constant.HttpHeaderConstant.X_CORRELATION_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        addMDCCorrelationId(request, response);
        addMDCUserId(request, response);

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        logRequest(requestWrapper);
        filterChain.doFilter(requestWrapper, responseWrapper);
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

        if (StringUtils.isBlank(queryString)) {
            log.info("Request: {} {}", method, uri);
        }
        else {
            log.info("Request: {} {}?{}", method, uri, queryString);
        }

        String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        String maskedBody = Utils.maskSensitiveJson(requestBody);
        log.debug("Request Body: {}", maskedBody);
    }

    private void logResponse(ContentCachingResponseWrapper response, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        int status = response.getStatus();
        String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        String maskedBody = Utils.maskSensitiveJson(responseBody);
        log.debug("Response Body: {}", maskedBody);
        log.info("Response: {}, Duration: {} ms", status, duration);
    }
}
