package ru.innopolis.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;
import springfox.documentation.annotations.ApiIgnore;

@RestController(value = "/api/admin")
@ApiIgnore
public class AdminRestController {

    private final RulesRepository rulesRepository;

    @Autowired
    public AdminRestController(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @GetMapping(value = "/getRule/{id}")
    public Rule getRule(@PathVariable("id") Long id) {
        return id == 0 ? new Rule() : getRule(id);
    }

    @Transactional
    @PostMapping()
    public Rule saveRule(Rule rule){
        return rulesRepository.save(rule);
    }



}
