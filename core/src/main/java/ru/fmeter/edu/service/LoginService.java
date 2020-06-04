package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
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

    public ResponseEntity<Boolean> register(UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.saveUser(new User(userDTO.getUserName(), userDTO.getPassword())),
                HttpStatus.OK);
    }

    public ResponseEntity<Boolean> activate(String key) {
        return null;
    }

    public ResponseEntity<String> login(LoginDTO login) {
        return null;
    }

    public ResponseEntity<Boolean> logout() {
        return null;
    }

    public void recover(String login) { }

    public ResponseEntity<Boolean> updatePassword(LoginDTO login) {
        return null;
    }
}
