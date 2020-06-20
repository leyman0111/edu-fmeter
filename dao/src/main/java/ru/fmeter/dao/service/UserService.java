package ru.fmeter.dao.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final RoleDao roleDao;

    public UserService(UserDao userDAO, RoleDao roleDao) {
        this.userDAO = userDAO;
        this.roleDao = roleDao;
    }

    @Transactional
    public boolean create(User user) {
        Optional<User> dbUser = userDAO.findUserByLoginOrEmail(user.getUsername(), user.getEmail());
        if (dbUser.isPresent()) return !dbUser.get().isEnabled();

        user.setPass(user.getPassword());
        Optional<Role> role = roleDao.findById(2L);
        if (role.isPresent()) {
            user.setRoles(Collections.singleton(role.get()));
            user.setActive(false);
            userDAO.save(user);
            return true;
        }
        return false;
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

    public void delete(Long id) {
        userDAO.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userDAO.deleteById(id);
    }
}
