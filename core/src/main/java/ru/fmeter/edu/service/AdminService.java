package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.Test;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.repo.TestDao;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;
import ru.fmeter.post.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TestDao testDao;
    private final PostService postService;

    public AdminService(UserService userService, UserMapper userMapper, TestDao testDao, PostService postService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.testDao = testDao;
        this.postService = postService;
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(userMapper.userToUserDto(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userService.findAll()) {
            users.add(userMapper.userToUserDto(user));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<String> blockUser(Long id) {
        Optional<User> userDB = userService.findById(id);
        if (userDB.isPresent()) {
            User user = userDB.get();
            user.setBlocked(user.isAccountNonLocked());
            if (userService.update(user)) {
                return new ResponseEntity<>("OK!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Can`t block or unblock user", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t find user", HttpStatus.OK);
    }

    public ResponseEntity<String> testUser(Long userId, Long testId) {
        Optional<User> user = userService.findById(userId);
        Optional<Test> test = testDao.findById(testId);
        if (user.isPresent() && test.isPresent()) {
            if (!test.get().isBlocked())
                return new ResponseEntity<>("Test is not blocked", HttpStatus.OK);
            if (user.get().getRating() < test.get().getThreshold())
                return new ResponseEntity<>("Insufficient user rating", HttpStatus.OK);
            if (!user.get().getLocal().equals(test.get().getLocal()))
                return new ResponseEntity<>("Inappropriate test language", HttpStatus.OK);
            String userSecretKey = SecretKeyStore.generate(user.get().getLogin());
            String testSecretKey = SecretKeyStore.generate(Long.toString(testId));
            postService.sendTestMail(user.get().getEmail(), userSecretKey, testSecretKey);
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t find user or test", HttpStatus.OK);
    }
}
