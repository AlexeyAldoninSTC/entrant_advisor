package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class RuleServiceImplTest {
    Ignite ignite;

    @Before
    public void setUp() {
        ignite = Ignition.start();
        IgniteCache<String, Rule> igniteCache = ignite.getOrCreateCache("ruleCache");
        igniteCache.put("rule2", new Rule(2L, "rule", Collections.emptySet()));
    }

    @Test
    public void getByName() {
        RulesRepository mock = Mockito.mock(RulesRepository.class);
        RuleServiceImpl ruleService = new RuleServiceImpl(ignite, mock);
        Mockito.when(mock.findByName("rule")).thenReturn(new Rule(2L, "rule", Collections.emptySet()));
        Rule rule = ruleService.getByName("rule");
        assertNotNull(rule);
        Assert.assertEquals(Long.valueOf(1), rule.getId());
        //spy
    }
}