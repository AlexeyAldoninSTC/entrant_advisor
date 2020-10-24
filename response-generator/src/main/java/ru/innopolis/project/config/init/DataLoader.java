package ru.innopolis.project.config.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final RulesRepository rulesRepository;

    public DataLoader(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Rule rule = new Rule();
        rule.setName("Rule1");
        Set<Condition> conditions = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Condition condition1 = new Condition();
            condition1.setFeatureName("Feature" + i);
            condition1.setOperation("Operation + i");
            condition1.setValue(i);
            condition1.setRule(rule);
            conditions.add(condition1);
        }
        rule.setConditions(conditions);
        rulesRepository.save(rule);*/
    }


}
