package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.ExamResultDto;
import ru.fmeter.dto.QuestionStatisticDto;
import ru.fmeter.dto.TestStatisticDto;
import ru.fmeter.edu.service.StatisticService;

import java.util.List;

@RestController
@RequestMapping("/admin/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<TestStatisticDto> getTestStatistic(@PathVariable Long id) {
        return statisticService.getLastTestStatistic(id);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<QuestionStatisticDto> getQuestionStatistic(@PathVariable Long id) {
        return statisticService.getLastQuestionStatistic(id);
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<ExamResultDto> getResultStatistic(@PathVariable Long id) {
        return statisticService.findResultStatisticDtoById(id);
    }

    @GetMapping("/results")
    public ResponseEntity<List<ExamResultDto>> getResultStatisticsByUserIdAndTestId(@RequestParam Long userId, @RequestParam Long testId) {
        return statisticService.findResultStatisticsDtoByUserIdAndTestId(userId, testId);
    }
}
