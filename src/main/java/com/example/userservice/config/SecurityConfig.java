package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final long MAX_AGE = 100;

    private final String corsAllowedOrigins ="*";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Collections.singletonList(corsAllowedOrigins));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD"));
                    configuration.setAllowedHeaders(List.of(
                            HttpHeaders.ORIGIN, HttpHeaders.ACCEPT,
                            HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION,
                            HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS
                    ));
                    configuration.setExposedHeaders(List.of(
                            HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                            HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                            HttpHeaders.CONTENT_DISPOSITION,
                            HttpHeaders.CONTENT_TYPE
                    ));
                    configuration.setMaxAge(MAX_AGE);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return configuration;
                }));
        return http.build();
    }
}
