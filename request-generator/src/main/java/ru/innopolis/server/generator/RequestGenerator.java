package ru.innopolis.server.generator;

import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.innopolis.server.config.RequestConfig;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
@ToString
public class RequestGenerator {
    private final RestTemplate restTemplate;
    private final RequestConfig requestConfig;
    private final String url;
    private final HttpHeaders httpHeaders;
    private JSONObject jsonObject;


    /**
     * При создании нашего генератора запросов, передаем ему все параметры.
     * С помощью параметров мы создаем объект requestConfig, который содержит
     * логику обработки параметров. Потом из него достаем все параметры:
     * url - URL - куда будем отправлять post запросы,
     * threadCount - количество потоков которые будут генерировать запросы,
     * requestCount - количество запросов общее которое нужно выполнить
     *
     * @param params - параметры необходимые для генерации запросов.
     */
    public RequestGenerator(Map<String, String> params) {
        requestConfig = new RequestConfig(params);
        url = requestConfig.getUrl();
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Основной метод нашего класса, для генерации запросов по заданному url.
     * Url приходит в параметрах от класса RequestConfig.
     * threadCount, requestCount приходят из requestConfig.
     * JsonObject сформированный приходит так же из requestConfig.
     * В самом начале метода вызывается метод testRequest(), если нет подключения к серверу
     * тогда мы не будем пытаться создать многопоточную версию запросов.
     *
     * @return
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    public Map<String, HashMap<String, Boolean>> generate() throws InterruptedException {
        testRequest();
        Map<String, HashMap<String, Boolean>> responses = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(requestConfig.getThreadCount());
        for (int i = 0; i < requestConfig.getCount(); i++) {
            executorService.execute(() -> {
                jsonObject = requestConfig.getJsonObject();
                HttpEntity<String> jsonObjectHttpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
                responses.put(jsonObject.get("features").toString(),
                        restTemplate.exchange(url, HttpMethod.POST, jsonObjectHttpEntity, HashMap.class).getBody());
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        return responses;
    }


    /**
     * Метод для тестового запроса к url, если подключения нет, тогда сразу падаем с ошибкой,
     * и не пытаемся даже создавать много потоков.
     */
    private void testRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(
                "{\"rules\" : [], \"features\" : {} }", headers), HashMap.class);
    }
}
