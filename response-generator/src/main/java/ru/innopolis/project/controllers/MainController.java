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
     * {"rules" : [ "mos", "kaz"],
     * "features" : {"math":"20",
     * "rus":"30"}}
     * В случае с парковками, машина должна сначала заехать,
     * {
     *     "rules": [
     *         "in"
     *     ],
     *     "features": {
     *         "number":"x111xx"
     *     }
     * }
     * потом нужно заплатить или при выезде указать что стояла машина меньше 20 мин.
     * Это если стояли меньше 20 мин
     * {
     *     "rules": [
     *         "out"
     *     ],
     *     "features": {
     *         "number":"x111xx",
     *         "out":"10"
     *     }
     * }
     * Если стояли больше 20 минут, тогда нужно заплать,
     * {
     *     "rules": [
     *         "pay"
     *     ],
     *     "features": {
     *         "number":"x111xx"
     *         }
     * }
     */
    @PostMapping("/api")
    public ResponseEntity<?> getCarCondition(@RequestBody JSONMessage entity) {

        Map<String, Boolean> execute = serviceLogic.execute(entity.getRules(), entity.getFeatures());
        return ResponseEntity.ok(execute);
    }
}
