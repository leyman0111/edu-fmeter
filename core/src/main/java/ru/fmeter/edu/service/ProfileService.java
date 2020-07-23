package ru.fmeter.edu.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.ProfileDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.OrganizationMapper;
import ru.fmeter.edu.mapper.ProfileMapper;
import ru.fmeter.edu.mapper.UserMapper;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserService userService, ProfileMapper profileMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.profileMapper = profileMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ProfileDto> getProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(profileMapper.userToProfile(user), HttpStatus.OK);
    }

    public ResponseEntity<String> updateProfile(ProfileDto profile) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!profile.getLogin().equals(user.getLogin()) && userService.isLoginExist(profile.getLogin())) {
            return new ResponseEntity<>("Login is already exist", HttpStatus.OK);
        }
        User u = profileMapper.profileToUser(profile);
        u.setPass(user.getPassword());
        u.setId(user.getId());
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
