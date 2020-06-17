package ru.fmeter.dao.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Role;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.repo.UserDao;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDAO;

    public UserService(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public boolean create(User user) {
        if (userDAO.findUserByLoginOrEmail(user.getUsername(), user.getEmail()).isPresent())
            return false;
        user.setPass(user.getPassword());
        user.setRoles(Collections.singleton(new Role(0L, "USER")));
        user.setActive(false);
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

    public User findById(Long id) {
        return userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean update(User user) {
        userDAO.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPass(user.getPass());
        userDAO.save(user);
        return true;
    }

    public boolean delete(Long id) {
        userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userDAO.deleteById(id);
        return false;
    }
}
