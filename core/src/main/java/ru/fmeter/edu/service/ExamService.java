package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.*;
import ru.fmeter.dao.repo.ExamResultDao;
import ru.fmeter.dao.repo.QuestionDao;
import ru.fmeter.dao.repo.TestDao;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.ExamDto;
import ru.fmeter.dto.ExamQuestionDto;
import ru.fmeter.dto.ExamResultDto;
import ru.fmeter.edu.mapper.ExamMapper;
import ru.fmeter.edu.mapper.ExamQuestionMapper;
import ru.fmeter.edu.mapper.ExamResultMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    private final TestDao testDao;
    private final QuestionDao questionDao;
    private final ExamResultDao examResultDao;
    private final ExamMapper examMapper;
    private final ExamQuestionMapper examQuestionMapper;
    private final ExamResultMapper examResultMapper;
    private final UserService userService;

    public ExamService(TestDao testDao, QuestionDao questionDao, ExamResultDao examResultDao,
                       ExamMapper examMapper, ExamQuestionMapper examQuestionMapper,
                       ExamResultMapper examResultMapper, UserService userService) {
        this.testDao = testDao;
        this.questionDao = questionDao;
        this.examResultDao = examResultDao;
        this.examMapper = examMapper;
        this.examQuestionMapper = examQuestionMapper;
        this.examResultMapper = examResultMapper;
        this.userService = userService;
    }

    public ResponseEntity<List<ExamDto>> findAll() {
        List<ExamDto> exams = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Test test : testDao.findAllByLocal(user.getLocale())) {
            if (!test.isBlocked()) {
                ExamDto exam = examMapper.testToExamDto(test);
                if (user.getRating() < exam.getThreshold()) {
                    exam.setAccess(false);
                    exam.setQuestions(null);
                }
                exams.add(exam);
            }
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    public ResponseEntity<ExamDto> find(Long id) {
        Optional<Test> test = testDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (test.isPresent() && !test.get().isBlocked() && user.getRating() >= test.get().getThreshold()) {
            return new ResponseEntity<>(examMapper.testToExamDto(test.get()), HttpStatus.OK);
        }
        return ResponseEntity.of(Optional.empty());
    }

    public ResponseEntity<ExamResultDto> postAnswers(Long id, HashMap<Long, String> answers) {
        Optional<Test> test = testDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (test.isPresent() && !test.get().isBlocked() && user.getRating() >= test.get().getThreshold()) {
            ExamResult result = checkAnswers(test.get(), answers, user.getId());
            if (result.isPassed()) {
                boolean contains = false;
                for (ExamResult r : examResultDao.findAllByUserIdAndTestId(user.getId(), test.get().getId())) {
                    if (r.isPassed()) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    user.setRating(user.getRating() + result.getAchievement());
                    userService.update(user);
                }
            }
            result = examResultDao.save(result);
            ExamResultDto resultDto = examResultMapper.examResultToDto(result);
            resultDto.setExam(examMapper.testToExamDto(test.get()));
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        }
        return ResponseEntity.of(Optional.empty());
    }

    public ResponseEntity<List<ExamResultDto>> getResults(Long id) {
        List<ExamResultDto> resultDtos = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ExamResult> results = examResultDao.findAllByUserIdAndTestId(user.getId(), id);
        if (!results.isEmpty()) {
            for (ExamResult r : results) {
                resultDtos.add(examResultMapper.examResultToDto(r));
            }
            return new ResponseEntity<>(resultDtos, HttpStatus.OK);
        }
        return ResponseEntity.of(Optional.empty());
    }

    public ResponseEntity<ExamQuestionDto> findQuestion(Long id) {
        Optional<Question> question = questionDao.findById(id);
        return question.map(value -> new ResponseEntity<>(examQuestionMapper.questionToExamQuestionDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<String> checkAnswer(Long id, String answer) {
        Optional<Question> questionDB = questionDao.findById(id);
        return questionDB.map(question -> question.getCorrect().equals(answer) ?
                new ResponseEntity<>("Correct", HttpStatus.OK) :
                new ResponseEntity<>("Fail", HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Question is not exist", HttpStatus.NOT_FOUND));
    }

    private ExamResult checkAnswers(Test test, HashMap<Long, String> answers, Long userId) {
        int achievement = 0;
        List<ExamAnswer> examAnswers = new ArrayList<>();
        for (Question question : test.getQuestions()) {
            String answer = answers.get(question.getId());
            boolean correct = answer.equals(question.getCorrect());
            if (correct) achievement += question.getPonderability();
            examAnswers.add(new ExamAnswer(question.getId(), answer, correct,
                    correct ? question.getPonderability() : 0));
        }
        return new ExamResult(userId, test.getId(), achievement, achievement >= test.getPassingScore(), examAnswers);
    }
}
