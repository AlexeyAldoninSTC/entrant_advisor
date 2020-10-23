package ru.innopolis.project.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RulesRepository rulesRepository;
    private static Map<String, Rule> tempRuleCache = new HashMap<>();

    @Autowired
    public AdminController(RulesRepository rulesRepository) {
        log.info("AdminController Constructor");
        this.rulesRepository = rulesRepository;
    }

    @GetMapping("/enter_name")
    public String getName() {
        return "enter_rule_name";
    }

    @PostMapping("/init_rule")
    public String createRuleWithName(String name, Model model) {
        Rule rule = new Rule();
        rule.setName(name);
        rule.setConditions(new HashSet<>());
        tempRuleCache.put(name, rule);
        model.addAttribute("rule", rule);
        model.addAttribute("newCondition", new Condition());
        return "create_new_rule";
    }

    @GetMapping("/fill_rule")
    public String createForm(Model model){
        model.asMap();
        return "create_new_rule";
    }

    @PostMapping(value = "/add_condition", params = "action=addCondition")
    public String addCondition(Model model, Rule rule, Condition newCondition) {
        Rule cached = tempRuleCache.get(rule.getName());
        cached.getConditions().add(newCondition);
        model.addAttribute("rule", cached);
        model.addAttribute("newCondition", new Condition());
        return "create_new_rule";
    }

    @PostMapping(value = "/add_condition", params = "action=saveRule")
    public String saveNewRule(@ModelAttribute Rule rule, Model model) {
        Rule cached = tempRuleCache.get(rule.getName());
        rulesRepository.save(cached);
        model.addAttribute("rule", cached);
        tempRuleCache.remove(cached.getName());
        return "display_saved_rule";
    }
}
