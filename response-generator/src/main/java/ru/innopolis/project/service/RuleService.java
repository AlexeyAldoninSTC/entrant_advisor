package ru.innopolis.project.service;

import ru.innopolis.project.entity.Rule;

import java.util.List;

public interface RuleService {

    Rule getByName(String ruleName);

    Rule save(Rule rule);

    void delete(Rule rule);

    List<Rule> getAll();
}
