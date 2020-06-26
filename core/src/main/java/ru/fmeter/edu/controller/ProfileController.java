package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.service.ProfileService;

@RestController
@RequestMapping("/me")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<UserDto> get() {
        return profileService.getProfile();
    }

    @PostMapping
    public ResponseEntity<String> update(@RequestBody UserDto userDto) {
        return profileService.updateProfile(userDto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return profileService.deleteProfile();
    }

    @PostMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody LoginDto loginDto) {
        return profileService.updatePassword(loginDto);
    }
}
