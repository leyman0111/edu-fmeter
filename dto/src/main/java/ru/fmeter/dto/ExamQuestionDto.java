package ru.fmeter.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ExamQuestionDto {
    private String wording;
    private HashMap<String, String> variants;
    private int ponderability;
    private String content;
}
