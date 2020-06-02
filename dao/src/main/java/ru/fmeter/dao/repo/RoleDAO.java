package ru.fmeter.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fmeter.dao.model.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
}
