package ru.fmeter.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String wording;
    private String variants;
    private String correct;
    private int ponderability;
    private String content;
}
