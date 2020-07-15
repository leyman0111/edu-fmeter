package ru.fmeter.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

@Data
public class TestStatisticDto {
    private Long testId;
    private Date createDate;
    private int attempts;
    private int firstTime;
    private int secondTime;
    private int moreTime;
    private int firstTimePercent;
    private int secondTimePercent;
    private int moreTimePercent;
    private HashMap<Long, Integer> hardestQuestions;
    private HashMap<Integer, Integer> achievements;
}
