package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.ProfileDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.ProfileMapper;
import ru.fmeter.edu.mapper.UserMapper;
import ru.fmeter.post.PostService;

import java.util.Optional;

@Service
public class LoginService {
    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final PostService postService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserService userService, ProfileMapper profileMapper, TokenService tokenService,
                        PostService postService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.profileMapper = profileMapper;
        this.tokenService = tokenService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> register(ProfileDto profile) {
        User user = profileMapper.profileToUser(profile);
        user.setPass(passwordEncoder.encode(profile.getPass()));
        user.setBlocked(true);
        user.setActive(false);
        if (userService.create(user)) {
            String secretKey = SecretKeyStore.generate(user.getLogin());
            postService.sendActivationMail(user.getEmail(), secretKey);
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
        try {
            User user = (User) userService.loadUserByUsername(login);
            if (!user.isEnabled()) {
                return new ResponseEntity<>("Account was not activated", HttpStatus.OK);
            }
            String secretKey = SecretKeyStore.generate(login);
            postService.sendRecoveryMail(user.getEmail(), secretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("User isn`t exist", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> recover(String secretKey, LoginDto loginDto) {
        Optional<String> login = SecretKeyStore.findLogin(secretKey);
        if (login.isPresent()) {
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
