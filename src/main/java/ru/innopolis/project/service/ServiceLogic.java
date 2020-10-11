package ru.innopolis.project.service;

import ru.innopolis.project.entity.pojo.Features;

import java.util.Map;

public interface ServiceLogic {

    Map<String, Boolean> execute(String[] rules, Features features);
}
