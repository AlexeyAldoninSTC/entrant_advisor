package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WAYS")
public class Way {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BALLS")
    @NotNull
    private int balls;

    @Column(name = "MATH")
    private int math;

    @Column(name = "RUS")
    private int rus;

    @Column(name = "ENG")
    private int eng;

    @Column(name = "INF")
    private int inf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FACULTY_ID")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Faculty faculty;
}
