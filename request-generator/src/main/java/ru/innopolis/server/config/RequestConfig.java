package ru.innopolis.server.config;

import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

@Getter
public class RequestConfig {
    private int count;
    private int threadCount;
    private JSONObject jsonObject;
    private String url;

    /**
     * Класс для корректной инициализации всех переданных переменных.
     * В этом же классе создаем jsonObject и добавляем туда rules.
     * @param map
     */
    public RequestConfig(Map<String, String> map) {
        if (map.get("count") != null && !map.get("count").isEmpty()) {
            count = Integer.parseInt(map.get("count"));
        }
        if (count < 1) {
            count = 1;
        }

        if (map.get("thread_count") != null && !map.get("thread_count").isEmpty()) {
            threadCount = Integer.parseInt(map.get("thread_count"));
        }
        if (threadCount < 1) {
            threadCount = 1;
        }

        url = map.get("url");
        jsonObject = new JSONObject();
        jsonObject.put("rules", new String[]{"fac1", "fac2", "fac3"});

    }
}
