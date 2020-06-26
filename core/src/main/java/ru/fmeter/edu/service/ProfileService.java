package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.OrganizationMapper;
import ru.fmeter.edu.mapper.UserMapper;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final OrganizationMapper organizationMapper;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserService userService, UserMapper userMapper,
                          OrganizationMapper organizationMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.organizationMapper = organizationMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDto> getProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userMapper.userToUserDto(user), HttpStatus.OK);
    }

    public ResponseEntity<String> updateProfile(UserDto userDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDto.getLogin().equals(user.getLogin()) && userService.isLoginExist(userDto.getLogin())) {
            return new ResponseEntity<>("Login is already exist", HttpStatus.OK);
        }
        user.setLogin(userDto.getLogin());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMidName(userDto.getMidName());
        user.setBirthday(userDto.getBirthday());
        user.setPosition(userDto.getPosition());
        user.setOrganization(organizationMapper.orgDtoToOrg(userDto.getOrganization()));
        if (userService.update(user)) {
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t update profile", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.delete(user.getId());
        return new ResponseEntity<>("OK!", HttpStatus.OK);
    }

    public ResponseEntity<String> updatePassword(LoginDto loginDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPass(passwordEncoder.encode(loginDto.getPassword()));
        if (userService.update(user)) {
            return new ResponseEntity<>("OK!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t update password", HttpStatus.OK);
    }
}
