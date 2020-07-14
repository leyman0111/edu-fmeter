package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cmn_test")
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int passingScore;
    private int threshold;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions;
    private String hardQuestions;
    private String complexity;
    private boolean isBlocked = false;
    private String local;
}
