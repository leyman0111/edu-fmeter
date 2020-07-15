package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cmn_test_statistic")
@Data
public class TestStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long testId;
    private Date createDate;
    private int attempts;
    private int passedTotal;
    private int firstTime;
    private int secondTime;
    private int moreTime;
    private int firstTimePercent;
    private int secondTimePercent;
    private int moreTimePercent;
    private String hardestQuestions;
    private String achievements;

    public TestStatistic(Long testId, int attempts, int passedTotal, int firstTime, int secondTime, int moreTime,
                         int firstTimePercent, int secondTimePercent, int moreTimePercent, String hardestQuestions,
                         String achievements) {
        this.testId = testId;
        this.createDate = new Date();
        this.attempts = attempts;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.moreTime = moreTime;
        this.firstTimePercent = firstTimePercent;
        this.secondTimePercent = secondTimePercent;
        this.moreTimePercent = moreTimePercent;
        this.hardestQuestions = hardestQuestions;
        this.achievements = achievements;
    }
}
