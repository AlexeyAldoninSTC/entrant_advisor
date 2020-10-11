package ru.innopolis.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conditions")
public class Condition {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "rules_id")
    private Long rulesId;

    @Column(name = "operation")
    private String operation;

    @Column(name = "feature")
    private String featureName;

    @Column(name = "value")
    private Integer value;
}
