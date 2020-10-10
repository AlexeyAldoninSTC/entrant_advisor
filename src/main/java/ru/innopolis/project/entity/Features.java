package ru.innopolis.project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Features {

    @JsonProperty(value = "math")
    private String math;

    @JsonProperty(value = "rus")
    private String rus;
}
