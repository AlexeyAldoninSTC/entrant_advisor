package ru.innopolis.server.config;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class RequestConfig {
    private int count;
    private int threadCount;
    private final String url;

    private final JSONObject jsonObject;
    private final List<String> rules;
    private final JSONObject features;
    private List<String> rulesList;


    /**
     * Класс для корректной инициализации всех переданных переменных.
     * В этом же классе формируем jsonObject.
     *
     * @param map
     */
    public RequestConfig(Map<String, String> map) {
        jsonObject = new JSONObject();
        features = new JSONObject();
        rules = new ArrayList<>();
        url = map.get("url");

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

        if (map.containsKey("carNumber")) {
            carAction(map);
        } else {
            entrantAction(map);
        }

        jsonObject.put("rules", rules);
        jsonObject.put("features", features);

    }

    public JSONObject getJsonObject() {
        JSONArray currentRules = (JSONArray) jsonObject.get("rules");
        Object rule = currentRules.get(0);

        if (!rule.equals("in") && !rule.equals("pay") && !rule.equals("out")) {
            rulesList.forEach(k -> {
                if (!k.isEmpty()) {
                    features.put(k, new Random().nextInt(100));
                }
            });
        }
        return jsonObject;
    }


    private void carAction(Map<String, String> map) {
        rules.add(map.get("action"));

        features.put("number", map.get("carNumber"));
        features.put("time", map.get("time"));
    }

    private void entrantAction(Map<String, String> map) {
        map.forEach((k, v) -> {
            if (k.contains("fac")) {
                rules.add(k);
            }
        });

        rulesList = map
                .keySet().stream()
                .filter(k -> k.contains("subject"))
                .map(map::get)
                .collect(Collectors.toList());
    }


}
