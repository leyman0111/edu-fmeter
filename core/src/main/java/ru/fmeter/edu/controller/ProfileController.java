package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.ProfileDto;
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
    public ResponseEntity<ProfileDto> get() {
        return profileService.getProfile();
    }

    @PostMapping
    public ResponseEntity<String> update(@RequestBody ProfileDto profile) {
        return profileService.updateProfile(profile);
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
