package ru.innopolis.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;

import java.util.List;
@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findAllByRule(Rule rule);

}
