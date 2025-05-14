package com.example.userservice.config.audit;

import com.example.userservice.dto.ApiContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuditorAware implements AuditorAware<String> {
    private final ApiContext apiContext;

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(apiContext.getUserId())
                .or(() -> Optional.of("SYSTEM"));
    }
}
