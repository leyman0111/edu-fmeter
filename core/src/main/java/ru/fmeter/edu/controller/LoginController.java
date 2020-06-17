package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.service.LoginService;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        return loginService.register(user);
    }

    @GetMapping("/activation/{key}")
    public ResponseEntity<String> activate(@PathVariable String key) {
        return loginService.activate(key);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto login) {
        return loginService.login(login);
    }

    @PostMapping("/recovery")
    public void recover(@RequestBody LoginDto login) { loginService.recover(login.getUsername()); }

    @PostMapping("/recovery/{key}")
    public ResponseEntity<String> updatePassword(@PathVariable String key, @RequestBody LoginDto loginDto) {
        return loginService.recover(key, loginDto);
    }
}
