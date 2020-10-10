package ru.innopolis.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.project.entity.DAOEntity;
import ru.innopolis.project.service.ServiceLogic;

@RestController
public class MainController {

    /**
     * При post запросе по пути /app парсим все это в объект DAOEntity
     * уже из него берем нужные поля features и rules.
     * {
     * "rules" : [ "mos", "kaz"],
     * "features" : {"math":"20",
     * "rus":"30"}
     * }
     */
    @PostMapping("/app")
    public ResponseEntity<?> get(@RequestBody DAOEntity entity) {


        ServiceLogic serviceLogic;

        System.out.println("Math features : " + entity.getFeatures().getMath());
        System.out.println("Rus features : " + entity.getFeatures().getRus());

        for (String rule : entity.getAllRules()) {
            System.out.println("Rule : " + rule);
        }

        return ResponseEntity.ok().build();
    }
}
