package ru.fmeter.dto;

import lombok.Data;

@Data
public class ExamAnswerDto {
    private Long id;
    private Long questionId;
    private String version;
    private boolean correct;
    private int point;
}
