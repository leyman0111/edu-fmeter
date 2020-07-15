package ru.fmeter.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ExamResultDto {
    private Long id;
    private Long userId;
    private ExamDto exam;
    private int achievement;
    private boolean passed;
    private List<ExamAnswerDto> answers;
}
