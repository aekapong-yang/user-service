package com.example.userservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PageResponse {
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
}
