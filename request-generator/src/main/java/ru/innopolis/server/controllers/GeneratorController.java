package ru.innopolis.server.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.server.generator.RequestGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/server")
public class GeneratorController {

    /**
     * Главная страница где указываются параметры:
     * количество потоков,
     * количество запросов,
     * URL адрес для выполнения запросов
     * @return
     */
    @GetMapping
    public String page() {
        return "main_page";
    }

    /**
     * Тут наш сервер принимает все переданные параметры,
     * создает requestGenerator, и если тестовая отправка запроса прошла удачно,
     * генерит запрсы на другой сервер.
     * При удачном завершении работы, на страницу в браузере мы выводим все ответы
     * от сервера, количество выполненных запросов, и время выполнения.
     * @param params - параметры которые прописываются в main_page
     * @param model
     * @return
     */
    @PostMapping("/start")
    public String startPage(@RequestParam Map<String, String> params,
                            Model model) {
        try {
            long startTime = System.currentTimeMillis();
            RequestGenerator requestGenerator = new RequestGenerator(params);
            List<HashMap<String, Boolean>> responses = requestGenerator.generate();
            long endTime = System.currentTimeMillis();

            model.addAttribute("request", responses);
            model.addAttribute("count", requestGenerator.getRequestConfig().getCount());
            model.addAttribute("time", (endTime - startTime));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't connect to server");
        }
        return "show_all";
    }

    @ExceptionHandler
    @ResponseBody
    public String exc(Throwable throwable){
        return throwable.getMessage();
    }

}
