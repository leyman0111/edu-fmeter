package ru.fmeter.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ExamQuestionDto {
    private Long id;
    private String wording;
    private int ponderability;
    private String content;
    private HashMap<String, String> variants;
}
