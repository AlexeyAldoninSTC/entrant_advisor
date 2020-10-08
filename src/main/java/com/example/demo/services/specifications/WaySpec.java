package com.example.demo.services.specifications;

import com.example.demo.entities.Way;
import org.springframework.data.jpa.domain.Specification;

public class WaySpec {

    public static Specification<Way> greaterThen(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("balls"), min);
    }
}
