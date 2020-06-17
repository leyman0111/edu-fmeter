package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;

@Service
public class LoginService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final SecretKeyService secretKeyService;

    public LoginService(UserService userService, UserMapper userMapper, TokenService tokenService, SecretKeyService secretKeyService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.secretKeyService = secretKeyService;
    }

    public ResponseEntity<String> register(UserDto userDTO) {
        if (userService.create(userMapper.userDtoToUser(userDTO))) {
            String secretKey = secretKeyService.generate(userDTO.getLogin());
            //todo: send secretKey to email
            System.out.println(secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login or email is already register", HttpStatus.OK);

    }

    public ResponseEntity<String> activate(String secretKey) {
        String username = secretKeyService.validate(secretKey);
        if (username == null) {
            return new ResponseEntity<>("Wrong secret key", HttpStatus.OK);
        }

        User user = (User) userService.loadUserByUsername(username);
        user.setActive(true);
        if (userService.update(user)) {
            secretKeyService.delete(secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t activate user", HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginDto login) {
        User user = (User) userService.loadUserByUsername(login.getUsername());
        if (user.getPass().equals(login.getPassword())) {
            String token = tokenService.generate(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect password", HttpStatus.OK);
    }
    public void recover(String login) {
        String secretKey = secretKeyService.generate(login);
        //todo: send secretKey to email
        System.out.println(secretKey);
    }

    public ResponseEntity<String> recover(String secretKey, LoginDto loginDto) {
        String username = secretKeyService.validate(secretKey);
        if (username == null || !username.equals(loginDto.getUsername())) {
            return new ResponseEntity<>("Wrong secret key", HttpStatus.OK);
        }
        User user = (User) userService.loadUserByUsername(username);
        user.setPass(loginDto.getPassword());
        if (userService.update(user)) {
            secretKeyService.delete(secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t update password", HttpStatus.OK);
    }
}
