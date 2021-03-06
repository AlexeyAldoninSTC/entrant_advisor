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
    private final ConditionRepository conditionRepository;
    private static final Map<String, Rule> TEMP_CONTROLLER_CACHE = new HashMap<>();

    @Autowired
    public AdminController(RuleService ruleService, ConditionRepository conditionRepository) {
        this.ruleService = ruleService;
        this.conditionRepository = conditionRepository;
    }

    @GetMapping("/enter_name")
    public String getName() {
        return "create_rule_name";
    }

    @PostMapping("/init_rule")
    public String createRuleWithName(String name, Model model) {
        if (name == null) {
            return "redirect:/admin/enter_name";
        }
        Rule byName = ruleService.getByName(name);
        if (byName != null) {
            model.addAttribute("rule", byName);
            String message = "Rule with name \"" + name + "\" already exists.";
            model.addAttribute("message", message);
            return "create_rule_name";
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

    @GetMapping("/find_rule")
    public String findByName() {
        return "find_rule_by_name";
    }

    @PostMapping(value = "/add_condition", params = "action=addCondition")
    public String addCondition(Model model, Rule rule, Condition newCondition) {
        if (rule == null) {
            return "redirect:/admin/";
        }
        Rule cached = TEMP_CONTROLLER_CACHE.get(rule.getName());
        cached.getConditions().add(newCondition);
        model.addAttribute("rule", cached);
        model.addAttribute("newCondition", new Condition());
        return "create_new_rule";
    }

    @PostMapping(value = "/add_condition", params = "action=saveRule")
    public String saveNewRule(@ModelAttribute Rule rule, Model model, Condition newCondition) {
        Rule cached = TEMP_CONTROLLER_CACHE.get(rule.getName());
        if (newCondition.getFeatureName() != null
                && newCondition.getOperation() != null
                && newCondition.getValue() != null) {
            cached.getConditions().add(newCondition);
        }
        if (cached.getConditions().isEmpty()) {
            String message = "At least 1 condition is required for a new Rule";
            model.addAttribute("message", message);
            model.addAttribute("rule", cached);
            model.addAttribute("newCondition", new Condition());
            return "create_new_rule";
        }
        cached.getConditions().forEach(condition -> condition.setRule(cached));
        ruleService.save(cached);
        model.addAttribute("rule", cached);
        TEMP_CONTROLLER_CACHE.remove(cached.getName());
        return "display_saved_rule";
    }

    @GetMapping("/rules")
    public String getAllRules(Model model) {
        List<Rule> rules = ruleService.getAll();
        model.addAttribute("rules", rules);
        return "all_rules";
    }

    @GetMapping(value = "/rules/details")
    public String getRuleDetailsById(String name, Model model) {
        Rule rule = ruleService.getByName(name);
        if (rule == null) {
            String message = "No rules found with name: " + name;
            model.addAttribute("message", message);
            return "find_rule_by_name";
        }
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
