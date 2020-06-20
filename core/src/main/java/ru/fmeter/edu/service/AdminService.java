package ru.fmeter.edu.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;

import java.util.List;

@Service
public class AdminService {
    private final UserService userService;
    private final UserMapper userMapper;

    public AdminService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        return null;
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        return null;
    }

    public ResponseEntity<String> blockUser(Long id) {
        return null;
    }
}
