package ru.innopolis.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.project.entity.Rule;

public interface RulesRepository extends JpaRepository<Rule, Long> {

    Rule findByName(String name);
}
