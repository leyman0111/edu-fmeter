package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.Question;

public interface QuestionDao extends JpaRepository<Question, Long> {
}
