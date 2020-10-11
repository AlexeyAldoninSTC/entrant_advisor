package ru.innopolis.project.service;


import java.util.Map;

public interface ServiceLogic {

    Map<String, Boolean> execute(String[] rules, Map<String, Integer> features);
}
