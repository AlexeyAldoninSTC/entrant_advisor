package ru.innopolis.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
public class MainController {

    /**
     * При post запросе по пути /app парсим все это
     * {
     *    "rules" : [ "mos", "kaz"],
     * "features" : {"math":"20",
     *                "rus":"30"}
     * }
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/app")
    public ResponseEntity get(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
        String[] rules = jsonMap.get("rules").toString()
                .replaceAll("\\p{Punct}","")
                .split("\\s+");
        Map<String, Object> features = (Map<String, Object>) jsonMap.get("features");

        features.forEach((k, v) -> System.out.println(k + " - " + v));
        Arrays.stream(rules).forEach(System.out::println);

//        rules - массив с названиями правил
//        features - мапа с параметрами

        return ResponseEntity.ok().build();
    }
}
