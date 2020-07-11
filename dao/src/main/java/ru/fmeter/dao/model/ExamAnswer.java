package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cmn_answer")
@Data
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long questionId;
    private String version;
    private boolean correct;
    private int point;

    public ExamAnswer() {
    }

    public ExamAnswer(Long questionId, String version, boolean correct, int point) {
        this.questionId = questionId;
        this.version = version;
        this.correct = correct;
        this.point = point;
    }
}
