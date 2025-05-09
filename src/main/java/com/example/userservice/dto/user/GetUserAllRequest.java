package com.example.userservice.dto.user;

import com.example.userservice.dto.PagingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAllRequest extends PagingRequest {
    private String searchValue;
}
