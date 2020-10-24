package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.project.entity.Car;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceLogicImpl implements ServiceLogic {

    @Autowired
    private final Ignite ignite;
    protected final ConditionRepository conditionRepository;
    protected final RulesRepository rulesRepository;
    private final IgniteCache<String, Car> igniteCache;
    private final IgniteCache<String, Integer> arrivalCount;

    public ServiceLogicImpl(Ignite ignite, ConditionRepository conditionRepository, RulesRepository rulesRepository) {
        this.ignite = ignite;
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
        igniteCache = ignite.getOrCreateCache("testCash");
        arrivalCount = ignite.getOrCreateCache("counter");

    }


    @Override
    public Map<String, Boolean> execute(String[] rules, Map<String, Object> features) {
        HashMap<String, Boolean> hashMap = new HashMap<>();

        for (String ruleName : rules) {
            Rule rule = rulesRepository.findByName(ruleName);
            if (rule == null) {
                hashMap.put(ruleName, false);
                continue;
            }

            Long ruleId = rule.getId();
            hashMap.put(ruleName, checkByOneRule(features, ruleId));
        }
        return hashMap;
    }

    private boolean checkByOneRule(Map<String, Object> features, Long ruleId) {
        for (Condition k : conditionRepository.findAllByRuleId(ruleId)) {
            Rule foundRule = rulesRepository.findById(ruleId).orElseThrow(() -> new RuntimeException("not found rule"));
            if (foundRule.getName().equals("in") || foundRule.getName().equals("out") || foundRule.getName().equals("pay")) {
                if (!checkOneRowForPark(k, features)) {
                    return false;
                }
            } else if (!checkOneRow(k, features)) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkOneRow(Condition condition, Map<String, Object> feature) {
        Object obj = feature.get(condition.getFeatureName());
        if (obj == null) {
            return false;
        }
        Integer s = Integer.parseInt(String.valueOf(obj));


        switch (condition.getOperation()) {
            case ">":
                return s > condition.getValue();
            case "<":
                return s < condition.getValue();
            case "=":
                return s.equals(condition.getValue());
            case ">=":
                return s >= condition.getValue();
            case "<=":
                return s <= condition.getValue();
        }

        return false;
    }

    protected boolean checkOneRowForPark(Condition condition, Map<String, Object> features) {
        String carNumber = String.valueOf(features.get("number"));

        if (carNumber == null) {
            throw new RuntimeException("Not car number");
        }

        switch (condition.getFeatureName()) {
            case "in":
                if (!igniteCache.containsKey(carNumber)) {
                    igniteCache.put(carNumber, new Car(LocalTime.parse((CharSequence) features.get("time")), false));
                    if (arrivalCount.containsKey(carNumber)) {
                        arrivalCount.put(carNumber, arrivalCount.get(carNumber) + 1);
                        return arrivalCount.get(carNumber) < condition.getValue();
                    } else {
                        arrivalCount.put(carNumber, 1);
                    }
                    return true;
                } else {
                    return false;
                }
            case "out":
                if (igniteCache.containsKey(carNumber)) {
                    LocalTime departureTime = LocalTime.parse((CharSequence) features.get("time"));
                    LocalTime arrivalTime = igniteCache.get(carNumber).getDate();
                    long time = Duration.between(arrivalTime, departureTime).toMinutes();
                    if (time < condition.getValue() || igniteCache.get(carNumber).isPay()) {
                        igniteCache.remove(carNumber);
                        return true;
                    } else return igniteCache.get(carNumber).isPay();

                } else {
                    return false;

                }
            case "pay":
                igniteCache.getAndReplace(carNumber, new Car(igniteCache.get(carNumber).getDate(), true));
                return true;
        }
        throw new IllegalArgumentException("I don't know what is it =\\");
    }

}
