package ru.fmeter.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Role;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.repo.RoleDAO;
import ru.fmeter.dao.repo.UserDAO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private BCryptPasswordEncoder passwordEncoder;

    //todo configuration
    public UserService(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findUserById(Long id) {
        return userDAO.findById(id).orElse(new User());
    }

    public List<User> allUsers() {
        return userDAO.findAll();
    }

    public boolean saveUser(User user) {
        Optional<User> userFromDB = userDAO.findByUserName(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userFromDB.isPresent()) return false;

        user.setRoles(Collections.singleton(new Role(0L, "USER")));
        userDAO.save(user);
        return true;
    }

    public boolean deleteUser(Long id) {
        if (userDAO.findById(id).isPresent()) {
            userDAO.deleteById(id);
            return true;
        }
        return false;
    }
}
