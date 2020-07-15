package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cmn_result")
@Data
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Date createDate;
    private Long testId;
    private int achievement;
    private boolean passed;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExamAnswer> answers;

    public ExamResult() { }

    public ExamResult(Long userId, Long testId, int achievement, boolean passed, List<ExamAnswer> answers) {
        this.userId = userId;
        this.testId = testId;
        this.achievement = achievement;
        this.passed = passed;
        this.answers = answers;
    }
}
