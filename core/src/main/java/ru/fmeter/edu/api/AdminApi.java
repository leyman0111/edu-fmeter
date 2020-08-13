package ru.fmeter.edu.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmeter.dao.model.Role;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminApi {
    private final AdminService adminService;

    public AdminApi(AdminService adminService) {
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

    @GetMapping("/test")
    public ResponseEntity<String> testUser(@RequestParam Long userId, @RequestParam Long testId) {
        return adminService.testUser(userId, testId);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return adminService.getRoles();
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<String> setRoles(@PathVariable Long userId, @RequestBody List<Long> rolesIds) {
        return adminService.setRoles(userId, rolesIds);
    }
}
