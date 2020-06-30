package ru.fmeter.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ExamDto {
    private String name;
    private int passingScore;
    private int threshold;
    private boolean access;
    private HashMap<Integer, ExamQuestionDto> questions;
}
