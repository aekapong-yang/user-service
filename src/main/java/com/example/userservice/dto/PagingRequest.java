package com.example.userservice.dto;

import com.example.userservice.constant.AppConstant;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PagingRequest {
    @Min(AppConstant.NUM_1)
    private int page = AppConstant.DEFAULT_PAGE;

    @Min(AppConstant.NUM_1)
    private int size = AppConstant.DEFAULT_SIZE;

    public int getPage() {
        return page - 1;
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}
