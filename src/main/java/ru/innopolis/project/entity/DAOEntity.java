package ru.innopolis.project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DAOEntity {

    @JsonProperty("rules")
    private String rules;

    @JsonProperty(value = "features")
    private Features features;

    public String[] getAllRules(){
        return rules.replaceAll("\\p{Punct}","")
                .split("\\s+");
    }

}
