package ru.fmeter.edu.api;

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
public class ExamApi {
    private final ExamService examService;

    public ExamApi(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamDto>> getExams() {
        return examService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> start(@PathVariable Long id) {
        return examService.find(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExamResultDto> end(@PathVariable Long id, @RequestBody HashMap<Long, String> answers) {
        return examService.postAnswers(id, answers);
    }

    @GetMapping("/results/{testId}")
    public ResponseEntity<List<ExamResultDto>> getResult(@PathVariable Long testId) {
        return examService.getResults(testId);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<ExamQuestionDto> getQuestion(@PathVariable Long id) {
        return examService.findQuestion(id);
    }

    @PostMapping("/questions/{id}")
    public ResponseEntity<String> postAnswer(@PathVariable Long id, @RequestBody String answer) {
        return examService.checkAnswer(id, answer);
    }

    @GetMapping("/final")
    public ResponseEntity<ExamDto> startFinalExam(@RequestParam String userSecretKey,
                                                  @RequestParam String testSecretKey) {
        return examService.find(userSecretKey, testSecretKey);
    }

    @PostMapping("/final")
    public ResponseEntity<ExamResultDto> endFinalExam(@RequestParam String userSecretKey,
                                                      @RequestParam String testSecretKey,
                                                      @RequestBody HashMap<Long, String> answers) {
        return examService.postAnswers(userSecretKey, testSecretKey, answers);
    }
}
