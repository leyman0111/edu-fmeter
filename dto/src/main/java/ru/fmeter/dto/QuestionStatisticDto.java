package ru.fmeter.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

@Data
public class QuestionStatisticDto {
    private Long questionId;
    private String createDate;
    private String answersStatistic;
    private String correct;
}
