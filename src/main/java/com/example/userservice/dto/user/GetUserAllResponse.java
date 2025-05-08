package com.example.userservice.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserAllResponse {
    private List<User> users;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private long id;
        private String username;
        private String email;
    }
}
