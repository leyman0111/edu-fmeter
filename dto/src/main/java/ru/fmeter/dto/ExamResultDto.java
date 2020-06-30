package ru.fmeter.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ExamResultDto {
    private int rating;
    private int passingScore;
    private boolean result;
    private HashMap<Integer, String> corrects;
    private HashMap<Integer, String> wrongs;
}
