package ru.innopolis.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.project.entity.pojo.JSONMessage;
import ru.innopolis.project.service.NewService;
import ru.innopolis.project.service.ServiceLogic;

import java.util.Map;

@RestController
public class MainController {

    private final NewService newService;

    @Autowired
    public MainController(NewService newService) {
        this.newService = newService;
    }

    /**
     * При post запросе по пути /api парсим все это в объект DAOEntity
     * уже из него берем нужные поля features и rules.
     *  {"rules" : [ "mos", "kaz"],
     * "features" : {"math":"20",
     * "rus":"30"}}
     */
//    @PostMapping("/api")
//    public ResponseEntity<?> get(@RequestBody JSONMessage entity) {
//
//        Map<String, Boolean> execute = serviceLogic.execute(entity.getRules(), entity.getFeatures());
//        return ResponseEntity.ok(execute);
//    }

    @PostMapping("/api2")
    public ResponseEntity<?> getCarCondition(@RequestBody JSONMessage entity) {

        Map<String, Boolean> execute = newService.execute(entity.getRules(), entity.getFeatures());
        return ResponseEntity.ok(execute);
    }
}
