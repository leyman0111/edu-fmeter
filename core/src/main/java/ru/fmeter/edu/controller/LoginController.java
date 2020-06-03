package ru.fmeter.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDTO;
import ru.fmeter.dto.UserDTO;
import ru.fmeter.dao.UserService;
import ru.fmeter.edu.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        return null;
    }

    @GetMapping("/activation")
    public ResponseEntity<String> activate(@RequestParam String key) {
        return null;
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return null;
    }

    @PostMapping("/recovery")
    public void recover(@RequestBody String login) { }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody LoginDTO login) {
        return null;
    }
}
