package com.example.demo.utils;

import com.example.demo.entities.Way;
import com.example.demo.services.specifications.WaySpec;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

@Getter
public class SearchFilter {

    private Specification<Way> specification;

    public SearchFilter(Map<String, String> requestParams,
                        List<Long> citiesId) {
        this.specification = Specification.where(null);

        String math = requestParams.get("math");
        String rus = requestParams.get("rus");
        String eng = requestParams.get("eng");
        String inf = requestParams.get("inf");
        String budget = requestParams.get("budget");
        int allBalls = 0;

        if (math != null) {
            int mathBall = Integer.parseInt(math);
            specification = specification.and(WaySpec.greaterThenMath(mathBall));
            allBalls += mathBall;
        }

        if (rus != null) {
            int rusBall = Integer.parseInt(rus);
            specification = specification.and(WaySpec.greaterThenRus(rusBall));
            allBalls += rusBall;
        }

        if (eng != null) {
            int engBall = Integer.parseInt(eng);
            specification = specification.and(WaySpec.greaterThenEng(engBall));
            allBalls += engBall;
        }

        if (inf != null) {
            int infBall = Integer.parseInt(inf);
            specification = specification.and(WaySpec.greaterThenInf(infBall));
            allBalls += infBall;
        }

        if (citiesId != null && !citiesId.isEmpty()){
            citiesId.forEach(id -> specification = specification.and(WaySpec.equalsCity(id)));
        }
        if (budget != null){
            specification = specification.and(WaySpec.greaterThenAllBalls(allBalls));
        }
    }
}
