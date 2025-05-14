package com.example.userservice.adaptor.logging;

import com.example.userservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        long startTime = System.currentTimeMillis();
        String serviceName = extractServiceNameFromUrl(request.getURI().toString());
        setMdcHeaders(request);

        log.info("[External: {}] Request: {} {}", serviceName, request.getMethod(), request.getURI());
        logRequestBody(body);

        ClientHttpResponse response = execution.execute(request, body);

        long duration = System.currentTimeMillis() - startTime;
        log.info("[External: {}] Response: {} Duration: {} ms", serviceName, response.getStatusCode(), duration);
        logResponseBody(response);

        return response;
    }

    private void logRequestBody(byte[] body) {
        if (body.length > 0) {
            String masked = Utils.maskSensitiveJson(new String(body, StandardCharsets.UTF_8));
            log.debug("Request Body: {}", masked);
        }
    }

    private void logResponseBody(ClientHttpResponse response) throws IOException {
        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        log.debug("Response Body: {}", body);
    }

    private void setMdcHeaders(HttpRequest request) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        if (contextMap != null) {
            contextMap.forEach((key, value) -> {
                if (key.startsWith("X-")) {
                    request.getHeaders().add(key, value);
                }
            });
        }
    }

    private String extractServiceNameFromUrl(String url) {
        if (url.contains("jsonplaceholder")) {
            return "JSON-PLACEHOLDER-SERVICE";
        }
        return "UNKNOWN-SERVICE";
    }
}
