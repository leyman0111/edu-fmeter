package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fmeter.dao.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
}
