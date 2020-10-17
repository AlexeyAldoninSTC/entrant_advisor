package ru.innopolis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.project.InMemoryDB.InMemoryDataBase;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceLogicImpl2 implements NewService {

    private final ConditionRepository conditionRepository;
    private final RulesRepository rulesRepository;
    private final InMemoryDataBase<String,Object> inMemoryDataBase = new InMemoryDataBase<>();

    @Autowired
    public ServiceLogicImpl2(ConditionRepository conditionRepository, RulesRepository rulesRepository) {
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
    }

    @Override
    public Map<String, Boolean> execute(String[] rules, Map<String, String> features) {
        HashMap<String, Boolean> hashMap = new HashMap<>();

        for (String ruleName : rules) {
            Rule rule = rulesRepository.findByName(ruleName);
            if (rule == null) {
                hashMap.put(ruleName, false);
                return hashMap;
            }

            String carNumber = features.get("number");
            if (rule.getName().equals("in")) {
                if (!inMemoryDataBase.containsKey("x031xx31")) {
                    inMemoryDataBase.save(carNumber, new Date());
                    hashMap.put(rule.getName(), true);
                    return hashMap;
                }else {
                    hashMap.put(rule.getName(),false);
                    return hashMap;
                }
            }else if (rule.getName().equals("out")){
                if (inMemoryDataBase.containsKey("x031xx31")){
                    hashMap.put(rule.getName(),true);
                    inMemoryDataBase.delete("x031xx31");
                    return hashMap;
                }else {
                    hashMap.put(rule.getName(),false);
                    return hashMap;
                }
            }
        }
        throw new IllegalArgumentException("I don't know what is it =\\");
    }
}
