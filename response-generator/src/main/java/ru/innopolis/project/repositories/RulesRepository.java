package ru.innopolis.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.project.entity.Rule;

@Repository
public interface RulesRepository extends JpaRepository<Rule, Long> {

    Rule findByName(String name);
}
