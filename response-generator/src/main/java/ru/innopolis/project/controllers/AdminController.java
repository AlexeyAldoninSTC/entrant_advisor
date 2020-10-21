package ru.innopolis.project.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;
import ru.innopolis.project.service.ServiceLogic;
import ru.innopolis.project.service.ServiceLogicImpl;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RulesRepository rulesRepository;

    @Autowired
    public AdminController(RulesRepository rulesRepository) {
        log.info("AdminController Constructor");
        this.rulesRepository = rulesRepository;
    }

    @GetMapping("/new")
    public String createForm(@RequestBody(required = false)Rule rule, Model model){
        System.out.println("inside controller");
        if (rule == null) {
            model.addAttribute("rule", new Rule());
        }
        model.addAttribute("rule", rule);
        return "create_new_rule";
    }

    @PostMapping(value = "/addCondition", params = "action=addCondition")
    public String addCondition(@RequestBody Rule rule, Model model) {
        rule.getConditions().add(new Condition());
        model.addAttribute("rule", rule);
        return "redirect:/create_new_rule";
    }

    @PostMapping(value = "/addCondition", params = "action=saveRule")
    public String saveNewRule(@RequestBody Rule rule, Model model) {
        rulesRepository.save(rule);
        model.addAttribute("rule", rule);
        return "redirect:/create_new_rule";
    }
}
