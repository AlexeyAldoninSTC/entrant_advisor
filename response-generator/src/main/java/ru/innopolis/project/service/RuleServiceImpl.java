package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.List;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

    private final RulesRepository rulesRepository;
    private final IgniteCache<String, Rule> igniteCache;

    public RuleServiceImpl(Ignite ignite, RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
        igniteCache = ignite.getOrCreateCache("ruleCache");
    }

    @Override
    public Rule getByName(String ruleName) {
        if (igniteCache.containsKey(ruleName)) {
            return igniteCache.get(ruleName);
        }
        Rule rule = rulesRepository.findByName(ruleName);
        if (rule != null) {
            igniteCache.put(ruleName, rule);
        }
        return rule;
    }

    @Override
    public Rule save(Rule rule){
        igniteCache.put(rule.getName(), rulesRepository.save(rule));
        return rule;
    }

    @Override
    public void delete(Rule rule) {
        rulesRepository.delete(rule);
        igniteCache.remove(rule.getName());
    }

    @Override
    public List<Rule> getAll() {
        return rulesRepository.findAll();
    }

}
