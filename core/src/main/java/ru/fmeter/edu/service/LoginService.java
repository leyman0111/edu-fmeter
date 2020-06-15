package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;
import ru.fmeter.edu.security.TokenAuthentication;

@Service
public class LoginService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public LoginService(UserService userService, UserMapper userMapper, TokenService tokenService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public ResponseEntity<Boolean> register(UserDto userDTO) {
        return new ResponseEntity<>(
                userService.create(userMapper.userDtoToUser(userDTO)),
                HttpStatus.OK);

    }

    public ResponseEntity<Boolean> activate(Long id, String key) {
        throw new UnsupportedOperationException("Operation temporarily unsupported");
    }

    public ResponseEntity<String> login(LoginDto login) {
        User user = (User) userService.loadUserByUsername(login.getUsername());
        if (user.getPass().equals(login.getPassword())) {
            String token = tokenService.generate(user.getId(), user.getLogin());
            Authentication userAuth =
                    new TokenAuthentication(token, user.getAuthorities(), true, user);
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("INCORRECT PASSWORD", HttpStatus.OK);
    }

    public ResponseEntity<Boolean> logout() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public void recover(String login) { }
}
