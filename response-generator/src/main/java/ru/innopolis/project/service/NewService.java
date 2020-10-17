package ru.innopolis.project.service;

import java.util.Map;

public interface NewService {
     Map<String, Boolean> execute(String[] incomeRule, Map<String, String> features);
}
