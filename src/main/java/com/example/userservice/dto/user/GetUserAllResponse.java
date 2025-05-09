package com.example.userservice.dto.user;

import com.example.userservice.dto.PageResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder("items")
public class GetUserAllResponse extends PageResponse {
    private List<User> items;

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
