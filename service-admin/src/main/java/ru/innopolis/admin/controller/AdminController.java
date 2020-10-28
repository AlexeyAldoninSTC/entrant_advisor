package ru.innopolis.admin.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;
import ru.innopolis.project.service.RuleService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RuleService ruleService;
    private final RulesRepository rulesRepository;
    private final ConditionRepository conditionRepository;
    private static final Map<String, Rule> TEMP_CONTROLLER_CACHE = new HashMap<>();

    @Autowired
    public AdminController(RuleService ruleService, RulesRepository rulesRepository, ConditionRepository conditionRepository) {
        this.ruleService = ruleService;
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
    }

    @GetMapping("/enter_name")
    public String getName() {
        return "create_rule_name";
    }

    @PostMapping("/init_rule")
    public String createRuleWithName(String name, Model model) {
        Rule byName = ruleService.getByName(name);
        if (byName != null) {
            model.addAttribute("rule", byName);
            return "rule_details";

        }
        Rule rule = new Rule();
        rule.setName(name);
        rule.setConditions(new HashSet<>());
        TEMP_CONTROLLER_CACHE.put(name, rule);
        model.addAttribute("rule", rule);
        model.addAttribute("newCondition", new Condition());
        return "create_new_rule";
    }

    @GetMapping("")
    public String createForm(){
        return "admin_main";
    }

    @PostMapping(value = "/add_condition", params = "action=addCondition")
    public String addCondition(Model model, Rule rule, Condition newCondition) {
        Rule cached = TEMP_CONTROLLER_CACHE.get(rule.getName());
        cached.getConditions().add(newCondition);
        model.addAttribute("rule", cached);
        model.addAttribute("newCondition", new Condition());
        return "create_new_rule";
    }

    @PostMapping(value = "/add_condition", params = "action=saveRule")
    public String saveNewRule(@ModelAttribute Rule rule, Model model, Condition newCondition) {
        Rule cached = TEMP_CONTROLLER_CACHE.get(rule.getName());
        cached.getConditions().add(newCondition);
        cached.getConditions().forEach(condition -> condition.setRule(cached));
        rulesRepository.save(cached);
        model.addAttribute("rule", cached);
        TEMP_CONTROLLER_CACHE.remove(cached.getName());
        return "display_saved_rule";
    }

    @GetMapping("/rules")
    public String getAllRules(Model model) {
        List<Rule> rules = rulesRepository.findAll();
        model.addAttribute("rules", rules);
        return "all_rules";
    }

    @GetMapping(value = "/rules/details")
    public String getRuleDetailsById(String name, Model model) {
        Rule rule = ruleService.getByName(name);
        model.addAttribute("rule", rule);
        return "rule_details";
    }

    @GetMapping(value = "/condition")
    public String conditionDetails(@RequestParam("id") Long id, Model model){
        Optional<Condition> optionalCondition = conditionRepository.findById(id);
        Condition condition = optionalCondition.orElse(new Condition());
        model.addAttribute("condition", condition);
        return "condition_update";
    }

    @PostMapping(value = "/delete_condition")
    public String deleteCondition(String name, Model model, Condition condition){
        Rule rule = ruleService.getByName(name);
        Iterator<Condition> iterator = rule.getConditions().iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(condition.getId())) {
                iterator.remove();
                break;
            }
        }
        ruleService.save(rule);
        model.addAttribute("rule", rule);
        return "rule_details";
    }

    @PostMapping("delete_rule")
    public String deleteRule(String name) {
        Rule rule = ruleService.getByName(name);
        ruleService.delete(rule);
        return "redirect:/admin/rules";
    }

    @PostMapping("/update_condition")
    public String updateCondition(String name, Condition condition, Model model) {
        Rule rule = ruleService.getByName(name);
        Iterator<Condition> iterator = rule.getConditions().iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(condition.getId())) {
                iterator.remove();
                break;
            }
        }
        condition.setRule(rule);
        rule.getConditions().add(condition);
        model.addAttribute("rule", ruleService.save(rule));
        return "rule_details";
    }

}
