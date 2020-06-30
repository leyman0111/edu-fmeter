package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.ExamDto;
import ru.fmeter.dto.ExamResultDto;
import ru.fmeter.dto.ExamQuestionDto;
import ru.fmeter.edu.service.ExamService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamDto>> getExams() {
        return examService.getExams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> start(@PathVariable Long id) {
        return examService.getExam(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExamResultDto> end(@PathVariable Long id, @RequestBody HashMap<Integer, String> answers) {
        return examService.checkAnswers(id, answers);
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<ExamResultDto> getResult(@PathVariable Long id) {
        return examService.getResult(id);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<ExamQuestionDto> getQuestion(@PathVariable Long id) {
        return examService.getQuestion(id);
    }

    @PostMapping("/questions/{id}")
    public ResponseEntity<String> postAnswer(@PathVariable Long id, @RequestBody String answer) {
        return examService.checkAnswer(id, answer);
    }
}
