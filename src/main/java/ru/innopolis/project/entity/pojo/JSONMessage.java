package ru.innopolis.project.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONMessage {

    @JsonProperty("rules")
    private String[] rules;

    @JsonProperty("features")
    private Map<String, Integer> features;

}
