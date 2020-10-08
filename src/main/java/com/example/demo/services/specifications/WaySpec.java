package com.example.demo.services.specifications;

import com.example.demo.entities.Way;
import org.springframework.data.jpa.domain.Specification;

public class WaySpec {

    public static Specification<Way> greaterThenAllBalls(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("balls"), min);
    }

    public static Specification<Way> greaterThenMath(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("math"), min);
    }

    public static Specification<Way> greaterThenRus(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("rus"), min);
    }

    public static Specification<Way> greaterThenEng(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("eng"), min);
    }

    public static Specification<Way> greaterThenInf(int min) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("inf"), min);
    }

    public static Specification<Way> equalsCity(Long cityId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("faculty").get("university").get("city").get("id"), cityId);
    }
}
