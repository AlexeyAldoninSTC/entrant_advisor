package ru.innopolis.server.generator;

import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.innopolis.server.config.RequestConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
public class RequestGenerator {
    private final RestTemplate restTemplate;
    private final RequestConfig requestConfig;
    private final String url;
    private final HttpHeaders httpHeaders;
    private final JSONObject jsonObject;
    private final int threadCount;
    private final int requestCount;

    /**
     * При создании нашего генератора запросов, передаем ему все параметры.
     * С помощью параметров мы создаем объект reqestConfig, который содержит
     * логику обработки параметров. Потом из него достаем все параметры:
     * url - URL - куда будем отправлять post запросы,
     * jsonObject в котором уже лежат rules,
     * threadCount - количество потоков которые будут генерировать запросы,
     * requestCount - количество запросов общее которое нужно выполнить
     *
     * @param params - параметры необходимые для генерации запросов.
     */
    public RequestGenerator(Map<String, String> params) {
        requestConfig = new RequestConfig(params);
        url = requestConfig.getUrl();
        jsonObject = requestConfig.getJsonObject();
        requestCount = requestConfig.getCount();
        threadCount = requestConfig.getThreadCount();
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Основной метод нашего класса, для генерации запросов по заданному url.
     * Url приходит в параметрах от класса RequestConfig.
     * В самом начале метода вызывается метод testRequest(), если нет подключения к серверу
     * тогда мы не будем пытаться создать многопоточную версию запросов.
     *
     * @return
     * @throws InterruptedException
     */
    public List<HashMap<String, Boolean>> generate() throws InterruptedException {
        testRequest();
        List<HashMap<String, Boolean>> responses = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < requestCount; i++) {
            executorService.execute(() -> {
                jsonObject.put("features", generateFeatures());
                HttpEntity<String> jsonObjectHttpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
                responses.add(restTemplate.exchange(url, HttpMethod.POST, jsonObjectHttpEntity, HashMap.class).getBody());
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        return responses;
    }

    /**
     * Метод для генерации случайных чисел в запросах.
     *
     * @return jsonObject с features : math и rus;
     */
    private JSONObject generateFeatures() {
        JSONObject featureObjects = new JSONObject();
        int random = (int) (Math.random() * 100);
        featureObjects.put("math", random);
        random = (int) (Math.random() * 100);
        featureObjects.put("rus", random);
        return featureObjects;
    }

    /**
     * Метод для тестового запроса к url, если подключения нет, тогда сразу падаем с ошибкой,
     * и не пытаемся даже создавать много потоков.
     */
    private void testRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(
                "{\"rules\" : [ \"mos\", \"kaz\"],\n" +
                "      \"features\" : {\"math\":\"20\",\n" +
                "      \"rus\":\"30\"}}", headers), HashMap.class).getBody();
    }
}
