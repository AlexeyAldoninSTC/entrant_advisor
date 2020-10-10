package ru.innopolis.project.service;

import ru.innopolis.project.entity.Features;

public interface ServiceLogic {

    int execute(String[] rules, Features features);
}
