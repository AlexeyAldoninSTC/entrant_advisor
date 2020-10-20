package ru.innopolis.server.config;

import lombok.Getter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class RequestConfig {
    private int count;
    private int threadCount;
    private final JSONObject jsonObject;
    private final String url;
    private List<String> rules;
    private final JSONObject features;


    /**
     * Класс для корректной инициализации всех переданных переменных.
     * В этом же классе создаем jsonObject и добавляем туда rules.
     *
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
        features = new JSONObject();
        rules = new ArrayList<>();

        if (map.containsKey("carNumber")) {
            carAction(map, rules);
        } else {
            entrantAction(map, rules);
        }


        jsonObject.put("rules", rules);


    }

    private void carAction(Map<String, String> map, List<String> rulesList) {
        rulesList.add(map.get("action"));
        features.put("number", map.get("carNumber"));
        features.put("time", map.get("time"));
    }

    private void entrantAction(Map<String, String> map, List<String> rulesList) {
        map.forEach((k, v) -> {
            if (k.contains("fac")) {
                rulesList.add(k);
            }
        });


        rules = map
                .keySet().stream()
                .filter(k -> k.contains("subject"))
                .map(map::get)
                .collect(Collectors.toList());
    }


}
