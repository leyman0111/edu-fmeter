package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.ExamResult;

import java.util.List;

public interface ExamResultDao extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findAllByUserIdAndTestId(Long userId, Long testId);
}
