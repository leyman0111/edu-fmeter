package ru.fmeter.edu.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dto.ExamDto;
import ru.fmeter.dto.ExamQuestionDto;
import ru.fmeter.dto.ExamResultDto;

import java.util.HashMap;
import java.util.List;

@Service
public class ExamService {
    public ResponseEntity<List<ExamDto>> getExams() {
        return null;
    }

    public ResponseEntity<ExamDto> getExam(Long id) {
        return null;
    }

    public ResponseEntity<ExamResultDto> checkAnswers(Long id, HashMap<Integer, String> answers) {
        return null;
    }

    public ResponseEntity<ExamResultDto> getResult(Long id) {
        return null;
    }

    public ResponseEntity<ExamQuestionDto> getQuestion(Long id) {
        return null;
    }

    public ResponseEntity<String> checkAnswer(Long id, String answer) {
        return null;
    }
}
