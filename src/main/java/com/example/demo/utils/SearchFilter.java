package com.example.demo.utils;

import com.example.demo.entities.Way;
import com.example.demo.services.WayService;
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
        int allBalls = 0;

        if (math != null) {
            allBalls += Integer.parseInt(math);
        }

        if (rus != null) {
            allBalls += Integer.parseInt(rus);
        }

        if (eng != null) {
            allBalls += Integer.parseInt(eng);
        }

        if (inf != null) {
            allBalls += Integer.parseInt(inf);
        }

        specification = specification.and(WaySpec.greaterThen(allBalls));


    }
}
