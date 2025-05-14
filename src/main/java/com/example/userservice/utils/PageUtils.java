package com.example.userservice.utils;

import com.example.userservice.dto.PageResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtils {
    public static <T> PageResponse.Pagination toPagination(Page<T> page) {
        return PageResponse.Pagination.builder()
                .page(page.getNumber() + 1)
                .size(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .totalElements((int) page.getTotalElements())
                .build();
    }
}
