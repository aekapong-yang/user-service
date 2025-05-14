package com.example.userservice.adaptor.config;

import com.example.userservice.adaptor.logging.LoggingInterceptor;
import com.example.userservice.exception.ExternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(100); // connection pool ทั้งหมด
        connManager.setDefaultMaxPerRoute(20); // ต่อ host

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return RestClient.builder()
                .requestFactory(factory)
                .requestInterceptor(new LoggingInterceptor())
                .defaultStatusHandler( response -> response.is4xxClientError() || response.is5xxServerError(),
                        (request, response) -> {
                            try (InputStream bodyStream = response.getBody()) {
                                String body = new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8);
                                log.error("Error Response [{} {}]: {}", request.getMethod(), request.getURI(), body);
                            } catch (IOException e) {
                                log.error("Failed to read error body", e);
                            }

                            // Optional: throw your own exception

                            throw new ExternalException("External call failed: " + response.getStatusCode(), response.getStatusCode());
                        })
                .build();
    }
}
