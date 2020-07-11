package ru.fmeter.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamDto {
    private Long id;
    private String name;
    private int passingScore;
    private int threshold;
    private boolean access = true;
    private List<ExamQuestionDto> questions;
}
