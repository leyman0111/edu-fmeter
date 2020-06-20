package ru.fmeter.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return adminService.getUser(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getUsers() {
        return adminService.getUsers();
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        return adminService.blockUser(id);
    }
}
