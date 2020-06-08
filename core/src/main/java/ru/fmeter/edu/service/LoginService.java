package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;

@Service
public class LoginService {
    private final UserService userService;
    private final UserMapper userMapper;

    public LoginService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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
        return null;
    }

    public ResponseEntity<Boolean> logout() {
        return null;
    }

    public void recover(String login) { }
}
