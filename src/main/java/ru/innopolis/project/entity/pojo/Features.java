package ru.innopolis.project.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Features {

    @JsonProperty(value = "math")
    private Integer math;

    @JsonProperty(value = "rus")
    private Integer rus;

    public Map<String, Integer> getMap() {
        return new HashMap<String, Integer>() {{
            put("math", math);
            put("rus", rus);
        }};
    }
}
