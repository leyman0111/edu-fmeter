package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;
import ru.fmeter.post.PostService;

import java.util.Optional;

@Service
public class LoginService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PostService postService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserService userService, UserMapper userMapper, TokenService tokenService,
                        PostService postService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> register(UserDto userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userService.create(userMapper.userDtoToUser(userDTO))) {
            String secretKey = SecretKeyStore.generate(userDTO.getLogin());
            System.out.println(secretKey);
            postService.sendActivationMail(userDTO.getEmail(), secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login or email is already registered", HttpStatus.OK);

    }

    public ResponseEntity<String> activate(String secretKey) {
        Optional<String> login = SecretKeyStore.findLogin(secretKey);
        if (login.isPresent()) {
            User user = (User) userService.loadUserByUsername(login.get());
            user.setActive(true);
            if (userService.update(user)) {
                SecretKeyStore.delete(secretKey);
                return new ResponseEntity<>("OK!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Can`t activate user", HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong secret key", HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginDto login) {
        User user = (User) userService.loadUserByUsername(login.getUsername());
        if (passwordEncoder.matches(login.getPassword(), user.getPassword()) && user.isEnabled() && user.isAccountNonLocked()) {
            String token = tokenService.generate(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<String> recover(String login) {
        User user = (User) userService.loadUserByUsername(login);
        if (user != null) {
            if (!user.isEnabled()) {
                return new ResponseEntity<>("Account was not activated", HttpStatus.OK);
            }
            String secretKey = SecretKeyStore.generate(login);
            System.out.println(secretKey);
            postService.sendRecoveryMail(user.getEmail(), secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t find username", HttpStatus.OK);
    }

    public ResponseEntity<String> recover(String secretKey, LoginDto loginDto) {
        Optional<String> login = SecretKeyStore.findLogin(secretKey);
        if (login.isPresent() && login.get().equals(loginDto.getUsername())) {
            User user = (User) userService.loadUserByUsername(login.get());
            user.setPass(passwordEncoder.encode(loginDto.getPassword()));
            if (userService.update(user)) {
                SecretKeyStore.delete(secretKey);
                return new ResponseEntity<>("OK!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Can`t update password", HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong secret key", HttpStatus.OK);
    }
}
