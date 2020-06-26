package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserService userService;
    private final UserMapper userMapper;

    public AdminService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(userMapper.userToUserDto(user), HttpStatus.OK);
        }
        return ResponseEntity.of(Optional.empty());
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userService.findAll()) {
            users.add(userMapper.userToUserDto(user));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<String> blockUser(Long id) {
        User user = userService.findById(id);
        if (user != null) {
            user.setBlocked(user.isAccountNonLocked());
            if (userService.update(user)) {
                return new ResponseEntity<>("OK!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Can`t block or unblock user", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t find user", HttpStatus.OK);
    }
}
