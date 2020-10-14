package ru.innopolis.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.project.entity.pojo.JSONMessage;
import ru.innopolis.project.service.ServiceLogic;

import java.util.Map;

@RestController
public class MainController {

    private final ServiceLogic serviceLogic;

    @Autowired
    public MainController(ServiceLogic serviceLogic) {
        this.serviceLogic = serviceLogic;
    }

    /**
     * При post запросе по пути /api парсим все это в объект DAOEntity
     * уже из него берем нужные поля features и rules.
     *  {"rules" : [ "mos", "kaz"],
     * "features" : {"math":"20",
     * "rus":"30"}}
     */
    @PostMapping("/api")
    public ResponseEntity<?> get(@RequestBody JSONMessage entity) {

        Map<String, Boolean> execute = serviceLogic.execute(entity.getRules(), entity.getFeatures());
        return ResponseEntity.ok(execute);
    }
}
