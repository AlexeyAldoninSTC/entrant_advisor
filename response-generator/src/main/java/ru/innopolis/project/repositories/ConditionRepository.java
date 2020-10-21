package ru.innopolis.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.project.entity.Condition;

import java.util.List;
@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findAllByRuleId(Long id);

}
