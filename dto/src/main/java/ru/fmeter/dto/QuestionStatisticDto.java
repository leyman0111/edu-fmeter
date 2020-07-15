package ru.fmeter.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

@Data
public class QuestionStatisticDto {
    private Long questionId;
    private Date createDate;
    private HashMap<String, Integer> answersStatistic;
    private String correct;
}
