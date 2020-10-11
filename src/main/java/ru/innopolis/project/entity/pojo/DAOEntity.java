package ru.innopolis.project.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DAOEntity {

    @JsonProperty("rules")
    private String[] rules;

    @JsonProperty(value = "features")
    private Features features;

}
