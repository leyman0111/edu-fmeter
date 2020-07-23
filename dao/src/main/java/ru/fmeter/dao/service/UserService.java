package ru.fmeter.dao.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Role;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.repo.RoleDao;
import ru.fmeter.dao.repo.UserDao;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDAO;
    private final RoleDao roleDao;

    public UserService(UserDao userDAO, RoleDao roleDao) {
        this.userDAO = userDAO;
        this.roleDao = roleDao;
    }

    @PostConstruct
    public void createRoles() {
        if (roleDao.findAll().size() > 0) {
            return;
        }
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        roles.add(Role.SUPER_ADMIN);
        roleDao.saveAll(roles);
    }

    @Transactional
    public boolean create(User user) {
        if (userDAO.findUserByLoginOrEmail(user.getLogin(), user.getEmail()).isPresent()) return false;

        user.setBlocked(true);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setLocal("en");
        user.setRating(0);
        userDAO.save(user);
        return true;
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        if (user.contains("@")) return findByEmail(user);
        else return findByLogin(user);
    }

    public User findByEmail(String email) throws UsernameNotFoundException {
        return userDAO.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByLogin(String login) throws UsernameNotFoundException {
        return userDAO.findUserByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    public boolean update(User user) {
        userDAO.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userDAO.save(user);
        return true;
    }

    public void delete(Long id) {
        userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userDAO.deleteById(id);
    }

    public boolean isLoginExist(String login) {
        return userDAO.findUserByLogin(login).isPresent();
    }
}
