package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cmn_question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wording;
    private String variants;
    private String correct;
    private int ponderability;
    private String content;
}
