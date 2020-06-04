package ru.fmeter.edu.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.UserService;
import ru.fmeter.dao.model.User;
import ru.fmeter.dto.LoginDTO;
import ru.fmeter.dto.UserDTO;

@Service
public class LoginService {
    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDTO> register(UserDTO userDTO) {
        return null;
    }

    public ResponseEntity<String> activate(String key) {
        return null;
    }

    public ResponseEntity<String> login(LoginDTO login) {
        return null;
    }

    public ResponseEntity<String> logout() {
        return null;
    }

    public void recover(String login) { }

    public ResponseEntity<String> updatePassword(LoginDTO login) {
        return null;
    }
}
