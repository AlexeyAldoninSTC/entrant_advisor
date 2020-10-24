package ru.innopolis.project.service;

import ru.innopolis.project.entity.Rule;

public interface RuleService {

    Rule getByName(String ruleName);
}
