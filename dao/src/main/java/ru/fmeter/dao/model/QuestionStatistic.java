package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cmn_question_statistic")
@Data
public class QuestionStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long questionId;
    private Date createDate;
    private String answersStatistic;
    private String correct;

    public QuestionStatistic(Long questionId, String answersStatistic, String correct) {
        this.questionId = questionId;
        this.createDate = new Date();
        this.answersStatistic = answersStatistic;
        this.correct = correct;
    }
}
