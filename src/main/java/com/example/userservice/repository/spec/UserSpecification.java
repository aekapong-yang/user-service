package com.example.userservice.repository.spec;

import com.example.userservice.dto.user.GetUserAllRequest;
import com.example.userservice.model.User;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {
    public static Specification<User> findUserAll(GetUserAllRequest request) {
        List<Predicate> predicates = new ArrayList<>();
        return ((root, query, cb) -> {
            if (StringUtils.isNotBlank(request.getSearchValue())) {
                predicates.add(cb.or(
                        cb.like(root.get("username"), request.getSearchValue() + "%"),
                        cb.like(root.get("email"), request.getSearchValue() + "%")
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
