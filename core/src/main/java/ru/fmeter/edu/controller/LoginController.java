package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDTO;
import ru.fmeter.dto.UserDTO;
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
        return loginService.register(user);
    }

    @GetMapping("/activation")
    public ResponseEntity<String> activate(@RequestParam String key) {
        return loginService.activate(key);
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        return loginService.login(login);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return loginService.logout();
    }

    @PostMapping("/recovery")
    public void recover(@RequestBody String login) { loginService.recover(login); }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody LoginDTO login) {
        return loginService.updatePassword(login);
    }
}
