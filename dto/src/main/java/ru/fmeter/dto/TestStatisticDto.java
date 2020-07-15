package ru.fmeter.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

@Data
public class TestStatisticDto {
    private Long testId;
    private String createDate;
    private int attempts;
    private int firstTime;
    private int secondTime;
    private int moreTime;
    private int firstTimePercent;
    private int secondTimePercent;
    private int moreTimePercent;
    private String hardestQuestions;
    private String achievements;
}
