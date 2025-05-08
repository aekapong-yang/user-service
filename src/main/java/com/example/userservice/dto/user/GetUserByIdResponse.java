package com.example.userservice.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByIdResponse {
    private long id;
    private String username;
    private String email;
}
