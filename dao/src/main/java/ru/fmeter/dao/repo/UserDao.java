package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmeter.dao.model.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByLoginOrEmail(String login, String email);
}
