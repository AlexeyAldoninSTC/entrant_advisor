package ru.innopolis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceLogicImpl implements ServiceLogic {

    private final ConditionRepository conditionRepository;
    private final RulesRepository rulesRepository;

    @Autowired
    public ServiceLogicImpl(ConditionRepository conditionRepository, RulesRepository rulesRepository) {
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
    }

    @Override
    public Map<String, Boolean> execute(String[] rules, Map<String, Integer> features) {
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

    private boolean checkByOneRule(Map<String, Integer> features, Long ruleId) {
        for (Condition k : conditionRepository.findAllByRulesId(ruleId)) {
            if (!checkOneRow(k, features)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOneRow(Condition condition, Map<String, Integer> feature) {
        Integer s = feature.get(condition.getFeatureName());
        String operation = condition.getOperation();

        if (s == null) {
            return false;
        }

        switch (operation) {
            case ">":
                return s > condition.getValue();
            case "<":
                return s < condition.getValue();
            case "=":
                return s.equals(condition.getValue());
        }

        return false;
    }
}
