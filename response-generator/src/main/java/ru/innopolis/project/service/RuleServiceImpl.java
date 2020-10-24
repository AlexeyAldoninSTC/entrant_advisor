package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;

public class RuleServiceImpl implements RuleService {

    private final Ignite ignite;
    private final RulesRepository rulesRepository;
    private final IgniteCache<String, Rule> igniteCache;

    public RuleServiceImpl(Ignite ignite, RulesRepository rulesRepository) {
        this.ignite = ignite;
        this.rulesRepository = rulesRepository;
        igniteCache = ignite.getOrCreateCache("ruleCache");
    }

    @Override
    public Rule getByName(String ruleName) {
        return igniteCache.getAndPutIfAbsent(ruleName, rulesRepository.findByName(ruleName));
    }
}
