package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.TestStatistic;

import java.util.Optional;

public interface TestStatisticDao extends JpaRepository<TestStatistic, Long> {
    Optional<TestStatistic> findFirstByTestIdOrderByCreateDateTimeDesc(Long id);
}
