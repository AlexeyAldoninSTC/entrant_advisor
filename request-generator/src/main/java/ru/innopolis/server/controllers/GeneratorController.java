package ru.innopolis.server.controllers;


import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import ru.innopolis.server.generator.RequestGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/server")
public class GeneratorController {

    private static final List<Map<String, HashMap<String, Boolean>>> cars = new ArrayList<>();

    /**
     * Главная страница где указываются параметры:
     * количество потоков,
     * количество запросов,
     * URL адрес для выполнения запросов
     *
     * @return
     */

    @GetMapping("/faculties")
    public String page(Model model) {

        model.addAttribute("features", new String[]{"math", "rus"});
        model.addAttribute("rules", new String[]{"fac1", "fac2", "fac3"});

        return "main_page";
    }

    @GetMapping("/parking")
    public String parkingPage(Model model) {
        model.addAttribute("cars", cars);
        return "parking_page";
    }

    /**
     * Тут наш сервер принимает все переданные параметры,
     * создает requestGenerator, и если тестовая отправка запроса прошла удачно,
     * генерит запрсы на другой сервер.
     * При удачном завершении работы, на страницу в браузере мы выводим все ответы
     * от сервера, количество выполненных запросов, и время выполнения.
     *
     * @param params - параметры которые прописываются в main_page
     * @param model
     * @return
     */
    @SneakyThrows
    @PostMapping("/start")
    public String startPage(@RequestParam Map<String, String> params,
                            Model model) {
        try {
            long startTime = System.currentTimeMillis();
            RequestGenerator requestGenerator = new RequestGenerator(params);
            Map<String, HashMap<String, Boolean>> responses = requestGenerator.generate();
            long endTime = System.currentTimeMillis();

            if (params.containsKey("carNumber")) {
                cars.add(responses);
                model.addAttribute("cars", cars);
                return "parking_page";
            }

            model.addAttribute("responses", responses);
            model.addAttribute("count", requestGenerator.getRequestConfig().getCount());
            model.addAttribute("time", (endTime - startTime));

        } catch (ResourceAccessException e) {
            throw new RuntimeException("Couldn't connect to server");
        }
        return "show_all";
    }


    @ExceptionHandler
    @ResponseBody
    public String exc(Throwable throwable) {
        return throwable.getMessage();
    }

}
