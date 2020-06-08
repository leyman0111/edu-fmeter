package ru.fmeter.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Role;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.repo.RoleDao;
import ru.fmeter.dao.repo.UserDao;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDao userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean create(User user) {
        if (userDAO.findUserByLoginOrEmail(user.getUsername(), user.getEmail()).isPresent())
            return false;
        user.setPass(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(0L, "USER")));
        userDAO.save(user);
        return true;
    }

    public User findById(Long id) {
        return userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByLogin(String user) {
        return (User) loadUserByUsername(user);
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return userDAO.findUserByLogin(user).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        return userDAO.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> allUsers() {
        return userDAO.findAll();
    }

    public boolean update(User user) {
        userDAO.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPass(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return true;
    }

    public boolean deleteUser(Long id) {
        userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userDAO.deleteById(id);
        return false;
    }
}
