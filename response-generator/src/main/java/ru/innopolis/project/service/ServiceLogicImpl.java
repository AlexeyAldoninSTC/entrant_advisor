package ru.innopolis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.project.InMemoryDB.InMemoryDataBase;
import ru.innopolis.project.entity.Car;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceLogicImpl implements ServiceLogic {

    protected final ConditionRepository conditionRepository;
    protected final RulesRepository rulesRepository;
    private final InMemoryDataBase inMemoryDataBase;


    @Autowired
    public ServiceLogicImpl(ConditionRepository conditionRepository, RulesRepository rulesRepository, InMemoryDataBase inMemoryDataBase) {
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
        this.inMemoryDataBase = inMemoryDataBase;
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
        }

        return false;
    }

    protected boolean checkOneRowForPark(Condition condition, Map<String, Object> features) {
        String carNumber = String.valueOf(features.get("number"));

        if (carNumber == null) {
            throw new RuntimeException("Not car number");
        }

        if (condition.getFeatureName().equals("in")) {
            if (!inMemoryDataBase.containsKey(carNumber)) {
                inMemoryDataBase.save(carNumber, new Car(new Date(), false));
                return true;
            } else {
                return false;
            }
        } else if (condition.getFeatureName().equals("out")) {
            if (inMemoryDataBase.containsKey(carNumber)) {
                if (!inMemoryDataBase.get(carNumber).isPay()) {
                    if (checkOneRow(condition, features)) {
                        inMemoryDataBase.delete(carNumber);
                        return true;
                    } else return false;
                }
                inMemoryDataBase.delete(carNumber);
                return true;
            } else {
                return false;
            }
        } else if (condition.getFeatureName().equals("pay")) {
            inMemoryDataBase.get(carNumber).setPay(true);
            return true;
        }
        throw new IllegalArgumentException("I don't know what is it =\\");
    }
}
