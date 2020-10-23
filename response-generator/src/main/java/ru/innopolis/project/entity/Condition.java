package ru.innopolis.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule rule;

    @Column(name = "operation")
    private String operation;

    @Column(name = "feature")
    private String featureName;

    @Column(name = "value")
    private Integer value;

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", rule=" + (rule != null ? rule.getName() : null) +
                ", operation='" + operation + '\'' +
                ", featureName='" + featureName + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Objects.equals(id, condition.id) &&
                Objects.equals(rule, condition.rule) &&
                Objects.equals(operation, condition.operation) &&
                Objects.equals(featureName, condition.featureName) &&
                Objects.equals(value, condition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rule, operation, featureName, value);
    }
}
