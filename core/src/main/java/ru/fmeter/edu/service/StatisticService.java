package ru.fmeter.edu.service;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.*;
import ru.fmeter.dao.repo.*;
import ru.fmeter.dto.ExamResultDto;
import ru.fmeter.dto.QuestionStatisticDto;
import ru.fmeter.dto.TestStatisticDto;
import ru.fmeter.edu.mapper.ExamResultMapper;
import ru.fmeter.edu.mapper.QuestionStatisticMapper;
import ru.fmeter.edu.mapper.TestStatisticMapper;

import java.util.*;

@Service
@EnableScheduling
public class StatisticService {
    private final TestStatisticDao testStatisticDao;
    private final QuestionStatisticDao questionStatisticDao;
    private final ExamResultDao resultDao;
    private final ExamAnswerDao answerDao;
    private final TestStatisticMapper testStatisticMapper;
    private final QuestionStatisticMapper questionStatisticMapper;
    private final ExamResultMapper resultMapper;
    private final TestDao testDao;

    public StatisticService(TestStatisticDao testStatisticDao, QuestionStatisticDao questionStatisticDao,
                            ExamResultDao resultDao, ExamAnswerDao answerDao,
                            TestStatisticMapper testStatisticMapper, QuestionStatisticMapper questionStatisticMapper,
                            ExamResultMapper resultMapper, TestDao testDao) {
        this.testStatisticDao = testStatisticDao;
        this.questionStatisticDao = questionStatisticDao;
        this.resultDao = resultDao;
        this.answerDao = answerDao;
        this.testStatisticMapper = testStatisticMapper;
        this.questionStatisticMapper = questionStatisticMapper;
        this.resultMapper = resultMapper;
        this.testDao = testDao;
    }

    @Scheduled(fixedDelay = 60000)
    private void prepareStatistics() {
        System.out.println("Preparing statistics is started");
        for (Test test : testDao.findAll()) {
            if (test.isStatisticRequired()) {
                prepareQuestionStatistic(test.getQuestions());
                List<ExamResult> results = resultDao.findAllByTestIdOrderByCreateDateAsc(test.getId());
                HashMap<String, Integer> attemptsAnalytics = analiseAttempts(results);
                testStatisticDao.save(
                        new TestStatistic(test.getId(), results.size(),
                                attemptsAnalytics.get("passedTotal"), attemptsAnalytics.get("firstTime"),
                                attemptsAnalytics.get("secondTime"), attemptsAnalytics.get("moreTime"),
                                attemptsAnalytics.get("firstTimePerc"), attemptsAnalytics.get("secondTimePerc"),
                                attemptsAnalytics.get("moreTimePerc"), analiseHardestQuestions(results),
                                analiseAchievements(results)));
                test.setStatisticRequired(false);
                testDao.save(test);
            }
        }
        System.out.println("Preparing statistics is completed");
    }

    private HashMap<String, Integer> analiseAttempts(List<ExamResult> results) {
        HashMap<String, Integer> analytics = new HashMap<>();
        int firstTime = 0;
        int secondTime = 0;
        int moreTime = 0;

        HashMap<Long, List<ExamResult>> sorted = new HashMap<>();
        for (ExamResult result : results) {
            if (!sorted.containsKey(result.getUserId()))
                sorted.put(result.getUserId(), new ArrayList<>());
            sorted.get(result.getUserId()).add(result);
        }

        for (Long userId : sorted.keySet()) {
            int time = 0;
            for (ExamResult result : sorted.get(userId)) {
                time++;
                if (result.isPassed()) {
                    switch (time) {
                        case (1):
                            firstTime++;
                            break;
                        case (2):
                            secondTime++;
                            break;
                        default:
                            moreTime++;
                            break;
                    }
                    break;
                }
            }
        }
        int passedTotal = firstTime + secondTime + moreTime;
        int firstTimePerc = 0;
        int secondTimePerc = 0;
        int moreTimePerc = 0;
        if (passedTotal != 0) {
            firstTimePerc = firstTime * 100 / passedTotal;
            secondTimePerc = secondTime * 100 / passedTotal;
            moreTimePerc = moreTime * 100 / passedTotal;
        }

        analytics.put("passedTotal", passedTotal);
        analytics.put("firstTime", firstTime);
        analytics.put("firstTimePerc", firstTimePerc);
        analytics.put("secondTime", secondTime);
        analytics.put("secondTimePerc", secondTimePerc);
        analytics.put("moreTime", moreTime);
        analytics.put("moreTimePerc", moreTimePerc);
        return analytics;
    }

    private String analiseHardestQuestions(List<ExamResult> results) {
        HashMap<Long, Integer> hardest = new HashMap<>();
        HashMap<Long, Integer> hardQuestions = new HashMap<>();
        for (ExamResult result : results) {
            for (ExamAnswer answer : result.getAnswers()) {
                if (!answer.isCorrect()) {
                    Long question = answer.getQuestionId();
                    hardQuestions.put(question, hardQuestions.get(question) != null ? hardQuestions.get(question) + 1 : 1);
                }
            }
        }
        hardQuestions.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<Long, Integer> entry : hardQuestions.entrySet()) {
            hardest.put(entry.getKey(), entry.getValue());
            if (hardest.size() == 3)
                break;
        }
        return new Gson().toJson(hardest);
    }

    private String analiseAchievements(List<ExamResult> results) {
        HashMap<Integer, Integer> analytics = new HashMap<>();
        for (ExamResult result : results) {
            int ach = result.getAchievement();
            analytics.put(ach, analytics.get(ach) != null ? analytics.get(ach) + 1 : 1);
        }
        return new Gson().toJson(analytics);
    }

    private void prepareQuestionStatistic(List<Question> questions) {
        for (Question question : questions) {
            HashMap<String, Integer> answerStatistic = new HashMap<>();
            for (ExamAnswer answer : answerDao.findAllByQuestionId(question.getId())) {
                String version = answer.getVersion();
                answerStatistic.put(version, answerStatistic.get(version) != null ? answerStatistic.get(version) + 1 : 1);
            }
            questionStatisticDao.save(
                    new QuestionStatistic(question.getId(), new Gson().toJson(answerStatistic), question.getCorrect())
            );
        }
    }

    public ResponseEntity<TestStatisticDto> getLastTestStatistic(Long id) {
        Optional<TestStatistic> testStatistic = testStatisticDao.findFirstByTestIdOrderByCreateDateDesc(id);
        return testStatistic.map(value -> new ResponseEntity<>(testStatisticMapper.testStatisticToDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<QuestionStatisticDto> getLastQuestionStatistic(Long id) {
        Optional<QuestionStatistic> questionStatistic = questionStatisticDao.findFirstByQuestionIdOrderByCreateDateDesc(id);
        return questionStatistic.map(value -> new ResponseEntity<>(questionStatisticMapper.questionStatisticToDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<ExamResultDto> findResultStatisticDtoById(Long id) {
        Optional<ExamResult> result = resultDao.findById(id);
        return result.map(value -> new ResponseEntity<>(resultMapper.examResultToDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<List<ExamResultDto>> findResultStatisticsDtoByUserIdAndTestId(Long userId, Long testId) {
        List<ExamResultDto> resultDtos = new ArrayList<>();
        List<ExamResult> results = resultDao.findAllByUserIdAndTestId(userId, testId);
        if (!results.isEmpty()) {
            for (ExamResult result : results) {
                resultDtos.add(resultMapper.examResultToDto(result));
            }
            return new ResponseEntity<>(resultDtos, HttpStatus.OK);
        }
        return ResponseEntity.of(Optional.empty());
    }
}
