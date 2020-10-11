package ru.innopolis.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.project.entity.Condition;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findAllByRulesId(Long id);
}
