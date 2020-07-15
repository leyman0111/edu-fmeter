package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.ExamAnswer;

import java.util.List;

public interface ExamAnswerDao extends JpaRepository<ExamAnswer, Long> {
    List<ExamAnswer> findAllByQuestionId(Long questionId);
}
