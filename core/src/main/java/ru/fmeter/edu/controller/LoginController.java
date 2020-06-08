package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> register(@RequestBody UserDto user) {
        return loginService.register(user);
    }

    @GetMapping("/activation/{userId}")
    public ResponseEntity<Boolean> activate(@PathVariable Long userId, @RequestParam String key) {
        return loginService.activate(userId, key);
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> login(@RequestBody LoginDto login) {
        return loginService.login(login);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        return loginService.logout();
    }

    @PostMapping("/recovery")
    public void recover(@RequestBody String login) { loginService.recover(login); }

    @PutMapping("/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody LoginDto login) {
        return loginService.updatePassword(login);
    }
}
