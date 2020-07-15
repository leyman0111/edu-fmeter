package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.QuestionStatistic;

import java.util.Optional;

public interface QuestionStatisticDao extends JpaRepository<QuestionStatistic, Long> {
    Optional<QuestionStatistic> findFirstByQuestionIdOrderByCreateDateDesc(Long id);
}
