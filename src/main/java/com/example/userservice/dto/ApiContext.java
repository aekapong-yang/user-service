package com.example.userservice.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@RequestScope
public class ApiContext {
    private String userId;
    private String username;
    private String userRole;
    private String branchCode;
    private boolean hasAllBranch;
    private List<String> roles;
}
