package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.Test;

import java.util.Optional;

public interface TestDao extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);
}
