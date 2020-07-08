package ru.fmeter.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestDto {
    private Long id;
    private String name;
    private int passingScore;
    private int threshold;
    private List<QuestionDto> questions;
    private String hardQuestions;
    private String complexity;
    private boolean isBlocked = false;
}
